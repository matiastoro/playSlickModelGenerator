package models
//import models.extensions.UserExtension
import extensions._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
//support for joda is now a separate project
import play.api.data.JodaForms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/



case class User(id: Option[Long] = None /*None*/,
                username: String = "" /*None*/,
                password: String = "" /*None*/,
                name: String = "" /*None*/,
                email: String = "" /*None*/) extends UserExtension{

}

object User {
  implicit val format = Json.format[User]
  val tupled = (this.apply _).tupled
}




case class UserFormData(obj: User, profileUsers: List[ProfileUserFormData]){
  def update(updatedObj: User = obj)(implicit repo: UserRepository, profileUserRepo: ProfileUserRepository, ec: ExecutionContext) = {
    //Delete elements that are not part of the form but they do exists in the database.
    profileUserRepo.byUserId(obj.id).map{ l =>  l.filterNot{o => profileUsers.exists(_.obj.id == o.id)}.map{profileUserRepo.delete(_)}}
    profileUsers.map{o => o.update(o.obj.copy(userId = obj.id.get))}
    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: User)(implicit repo: UserRepository, profileUserRepo: ProfileUserRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future.sequence(    profileUsers.map{o => o.insert(o.obj.copy(userId = id))}).map{ x =>
        id
      }
    
    }
  }
}
object UserFormData{
  def apply(obj: User)(implicit repo: UserRepository, profileUserRepo: ProfileUserRepository, ec: ExecutionContext) = {
    for{
      profileUsers <- profileUserRepo.byUserId(obj.id).map(l => l.map(ProfileUserFormData(_)))
    } yield{
      new UserFormData(obj, profileUsers.toList)
    }
  }
}
object UserForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "profileUsers" -> list(models.ProfileUserForm.form.mapping)
    )/*(User.apply)(User.unapply)*/
    ((id,username,password,name,email,profileUsers) => {
      UserFormData(User(id, username, password, name, email), profileUsers)
    })((formData: UserFormData) => {
      Some((formData.obj.id, formData.obj.username, formData.obj.password, formData.obj.name, formData.obj.email, formData.profileUsers))
    })
  )
}