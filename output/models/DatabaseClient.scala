package models

import play.api.db.DB
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import util.Via56Logging._
import com.mchange.v2.c3p0.ComboPooledDataSource


object db{
  def createDB = {
    //Aerodromes.createDB
    Fpls.createDB
    Operations.createDB
  }
}

case class PaginatedResults[T](results: List[T], count: Int, page: Int, pageLength: Int)

/** DatabaseClient trait.
  *
  * It provides a common interface for all database access classes.
  */
trait DatabaseClient[E] {
  /** the corresponding table in the DB, must have an id */
  type DBTable <: Table[E]{def id: Column[Long]}

  /** reference to all elements in the table */
  private[models] val all: TableQuery[DBTable]

  /** database "name" */
  private def dbName = if (play.Play.isTest) "test" else "default"
  /** the actual database, instanced by its name */

  /*def database = {
    val ds = new ComboPooledDataSource
    ds.setDriverClass(play.api.Play.current.configuration.getString("db."+dbName+".driver").getOrElse(""))
    ds.setJdbcUrl(play.api.Play.current.configuration.getString("db."+dbName+".url").getOrElse(""))
    ds.setUser(play.api.Play.current.configuration.getString("db."+dbName+".user").getOrElse(""))
    ds.setPassword(play.api.Play.current.configuration.getString("db."+dbName+".password").getOrElse(""))
    ds.setMinPoolSize(play.api.Play.current.configuration.getInt("db."+dbName+".min_pool_size").getOrElse(1))
    ds.setAcquireIncrement(play.api.Play.current.configuration.getInt("db."+dbName+".aquire_increment").getOrElse(1))
    ds.setMaxPoolSize(play.api.Play.current.configuration.getInt("db."+dbName+".max_pool_size").getOrElse(3))
    Database.forDataSource(ds)
  }*/



  val database = Database.forDataSource(DB.getDataSource(dbName))
  /** database access for a session (many transactions).
    * It is required for any attempt to access the database,
    * regardless of the context */
  def withSession[T](f: Session => T): T = database.withSession(f)
  /** database access for a single transaction */
  def withTransaction[T](f: Session => T): T = database.withTransaction(f)

  /** database creation */
  def createDB = database.withSession { implicit db: Session =>
    try{
      all.ddl.create
    } catch {
      case e: Exception => error("Could not create database.... assuming it already exists")
    }
  }

  /** all the elements on the table
   *
   * @return all elements on table E
   */
  def getAll: List[E] = database.withSession {implicit db: Session =>
    all.list
  }
  /** returns the query that looks for an element by id
    *
    * @return the element with the given id, if any
    */
  def byIdQuery(id: Long) = database.withSession {implicit db: Session =>
    all.filter(_.id === id)
  }
  /** looks for an element by id
    *
    * @return the element with the given id, if any
    */
  def byId(id: Long): Option[E] = database.withSession {implicit db: Session =>
    byIdQuery(id).list.headOption
  }

  /** table insert, in SQL fashion
    *
    * @return the id of the inserted element */
  def insert(obj: E): Long = database.withSession { implicit db: Session =>
    (all returning all.map(_.id)) += obj
  }

  /** table update, in SQL fashion. Objects without an id CANNOT be updated  */
  def update(obs: E{def id: Option[Long]}) = database.withSession { implicit db: Session =>
    obs.id.map{
      id => all.filter(_.id === id).update(obs)
    }
  }

  /** table truncate, in SQL fashion */
  def deleteAll = database.withSession { implicit db: Session =>
    all.delete
  }

  /** table delete, in SQL fashion. Objects without an id CANNOT be deleted  */
  def delete(obs: E{def id: Option[Long]}) = database.withSession { implicit db: Session =>
    obs.id.map {
      id => all.filter(_.id === id).delete
    }
  }

  /** Function to lazily filter by many parameters at the same time, with "or"
    *
    * @param f the filter function, given the filter parameter
    * @param l the list of filter parameters
    * @param n the db element
    * @return a ready-to-use filter operation
    */
  def unionFilters(f: (DBTable, String) => Column[Boolean],
                           l: List[String], n: DBTable): Column[Boolean] = {
    if (l.size >= 2)
      l.tail.foldLeft(f(n,l.head))((b, s) => b || f(n,s))
    else if (l.size == 1)
      f(n,l.head)
    else
      f(n,"")
  }

  /** Function to lazily filter by many parameters at the same time, "with and"
    *
    * @param f the filter function, given the filter parameter
    * @param l the list of filter parameters
    * @param n the db element
    * @return a ready-to-use filter operation
    */
  def intersectionFilters(f: (DBTable, String) => Column[Boolean],
                          l: List[String], n: DBTable): Column[Boolean] = {
    if (l.size >= 2)
      l.tail.foldLeft(f(n,l.head))((b, s) => b && f(n,s))
    else if (l.size == 1)
      f(n,l.head)
    else
      f(n,"")
  }


  def paginate[E,U,C[_]](q: Query[E,U,C], pageLength: Int, page: Int) = database.withSession { implicit db: Session =>
    PaginatedResults(q.drop((page - 1) * pageLength).take(pageLength).list, q.length.run, page, pageLength)
  }


  def list[E,U,C[_]](q: Query[E,U,C]) =  database.withSession { implicit db: Session =>
    q.list
  }



}