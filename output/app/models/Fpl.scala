package models
import models.extensions.FplExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.json._

case class Fpl(id: Option[Long] = None /*None*/,
               mainFields: MainFields,
               mainFields2: MainFields2,
               supplementaryFields: SupplementaryFields,
               supplementaryFields2: SupplementaryFields2,
               timeFields: TimeFields,
               userFields: UserFields,
               messageFields: MessageFields,
               state: String = "1" /*Hidden*/,
               bitCtrl: Int = 0 /*Hidden*/,
               createdAt: Option[DateTime] = None /*Hidden*/,
               otherFields: OtherFields,
               fplId: Option[Int] = None /*Hidden*/) extends FplExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}

case class MainFields(callsign: String = "" /*None*/,
                      ssr: Option[String] = None /*Hidden*/,
                      flightRules: String = "" /*None*/,
                      typeOfFlight: Option[String] = None /*None*/,
                      number: Option[Int] = None /*None*/,
                      typeOfAircraft: String = "" /*None*/,
                      wakeTurbulenceCategory: String = "" /*None*/,
                      radiocomEquipment: String = "" /*None*/,
                      survellianceEquipment: String = "" /*None*/,
                      departure: String = "" /*None*/,
                      etdHour: String = "" /*None*/,
                      speedUnit: String = "" /*None*/,
                      speed: String = "" /*None*/,
                      levelUnit: String = "" /*None*/,
                      level: String = "" /*None*/,
                      route: String = "" /*None*/,
                      destination: String = "" /*None*/,
                      eet: String = "" /*None*/) extends MainFieldsExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class MainFields2(alternative1: Option[String] = None /*None*/,
                       alternative2: Option[String] = None /*None*/,
                       otherInformation: String = "" /*None*/) extends MainFields2Extension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class SupplementaryFields(endurance: Option[String] = None /*None*/,
                               personsOnBoard: Option[Int] = None /*None*/,
                               radioUhf: Option[Boolean] = None /*None*/,
                               radioVhf: Option[Boolean] = None /*None*/,
                               radioElba: Option[Boolean] = None /*None*/,
                               survivalEquipment: Option[Boolean] = None /*None*/,
                               survivalEquipmentPolar: Option[Boolean] = None /*None*/,
                               survivalEquipmentDesert: Option[Boolean] = None /*None*/,
                               survivalEquipmentMaritime: Option[Boolean] = None /*None*/,
                               survivalEquipmentJungle: Option[Boolean] = None /*None*/,
                               jackets: Option[Boolean] = None /*None*/,
                               jacketsLights: Option[Boolean] = None /*None*/,
                               jacketsFluor: Option[Boolean] = None /*None*/,
                               jacketsUhf: Option[Boolean] = None /*None*/,
                               jacketsVhf: Option[Boolean] = None /*None*/,
                               dinghies: Option[Boolean] = None /*None*/,
                               dinghiesNumber: Option[Int] = None /*None*/,
                               dinghiesCapacity: Option[Int] = None /*None*/) extends SupplementaryFieldsExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class SupplementaryFields2(dinghiesCover: Option[Boolean] = None /*None*/,
                                dinghiesColor: Option[String] = None /*None*/,
                                aircraftColorAndMarkings: Option[String] = None /*None*/,
                                remarks: Option[String] = None /*None*/) extends SupplementaryFields2Extension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class TimeFields(etd: DateTime = new DateTime() /*Hidden*/,
                      eta: DateTime = new DateTime() /*Hidden*/,
                      atd: Option[String] = None /*Hidden*/,
                      ata: Option[String] = None /*Hidden*/) extends TimeFieldsExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class UserFields(userId: Option[Int] = None /*Hidden*/,
                      pilotIdNumber: Option[String] = None /*Hidden*/,
                      pilotName: Option[String] = None /*Hidden*/,
                      pilotSurname: Option[String] = None /*Hidden*/,
                      pilotOtherSurname: Option[String] = None /*Hidden*/,
                      pilotLicense: Option[String] = None /*Hidden*/,
                      issuerIdNumber: Option[String] = None /*Hidden*/,
                      issuerName: Option[String] = None /*Hidden*/,
                      issuerSurname: Option[String] = None /*Hidden*/,
                      issuerOtherSurname: Option[String] = None /*Hidden*/,
                      issuerLicense: Option[String] = None /*Hidden*/) extends UserFieldsExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class MessageFields(message: Option[String] = None /*Hidden*/,
                         ci: Option[String] = None /*Hidden*/,
                         csn: Option[Int] = None /*Hidden*/) extends MessageFieldsExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



case class OtherFields(aircraftExpirationDate: Option[String] = None /*Hidden*/,
                       observation: Option[Boolean] = None /*Hidden*/,
                       issueDate: Option[DateTime] = None /*Hidden*/,
                       messageDate: Option[DateTime] = None /*Hidden*/) extends OtherFieldsExtension{
  implicit val jsonFormat = Json.format[Fpl]
  def toJson = Json.toJson(this)
         

  lazy val selectString = state

}



class Fpls(tag: Tag) extends Table[Fpl](tag, "fpl") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def callsign = column[String]("matricula", O.Default(""))
  def ssr = column[Option[String]]("transpondedor", O.Default(None))
  def flightRules = column[String]("regla_vuelo", O.Default(""))
  def typeOfFlight = column[Option[String]]("tipo_vuelo", O.Default(None))
  def number = column[Option[Int]]("numero_aviones", O.Default(None))
  def typeOfAircraft = column[String]("tipo_aeronave", O.Default(""))
  def wakeTurbulenceCategory = column[String]("estela", O.Default(""))
  def radiocomEquipment = column[String]("equipo_radiocom", O.Default(""))
  def survellianceEquipment = column[String]("equipo_vigilancia", O.Default(""))
  def departure = column[String]("salida", O.Default(""))
  def etdHour = column[String]("hora_etd", O.Default(""))
  def speedUnit = column[String]("velocidad_unidad", O.Default(""))
  def speed = column[String]("velocidad", O.Default(""))
  def levelUnit = column[String]("nivel_unidad", O.Default(""))
  def level = column[String]("nivel", O.Default(""))
  def route = column[String]("ruta", O.Default(""))
  def destination = column[String]("destino", O.Default(""))
  def eet = column[String]("hora_ete", O.Default(""))
  val mainFieldsCols = (callsign, ssr, flightRules, typeOfFlight, number, typeOfAircraft, wakeTurbulenceCategory, radiocomEquipment, survellianceEquipment, departure, etdHour, speedUnit, speed, levelUnit, level, route, destination, eet)


  def alternative1 = column[Option[String]]("alter1", O.Default(None))
  def alternative2 = column[Option[String]]("alter2", O.Default(None))
  def otherInformation = column[String]("otros_datos", O.Default(""))
  val mainFields2Cols = (alternative1, alternative2, otherInformation)


  def endurance = column[Option[String]]("autonomia", O.Default(None))
  def personsOnBoard = column[Option[Int]]("pasajeros", O.Default(None))
  def radioUhf = column[Option[Boolean]]("radio_uhf", O.Default(None))
  def radioVhf = column[Option[Boolean]]("radio_vhf", O.Default(None))
  def radioElba = column[Option[Boolean]]("radio_e", O.Default(None))
  def survivalEquipment = column[Option[Boolean]]("equipo_supervivencia", O.Default(None))
  def survivalEquipmentPolar = column[Option[Boolean]]("equipo_polar", O.Default(None))
  def survivalEquipmentDesert = column[Option[Boolean]]("equipo_desertico", O.Default(None))
  def survivalEquipmentMaritime = column[Option[Boolean]]("equipo_maritimo", O.Default(None))
  def survivalEquipmentJungle = column[Option[Boolean]]("equipo_jungla", O.Default(None))
  def jackets = column[Option[Boolean]]("jackets", O.Default(None))
  def jacketsLights = column[Option[Boolean]]("chaleco_luz", O.Default(None))
  def jacketsFluor = column[Option[Boolean]]("chaleco_fluorecente", O.Default(None))
  def jacketsUhf = column[Option[Boolean]]("chaleco_uhf", O.Default(None))
  def jacketsVhf = column[Option[Boolean]]("chaleco_vhf", O.Default(None))
  def dinghies = column[Option[Boolean]]("bote_neumatico", O.Default(None))
  def dinghiesNumber = column[Option[Int]]("bote_cantidad", O.Default(None))
  def dinghiesCapacity = column[Option[Int]]("bote_capacidad", O.Default(None))
  val supplementaryFieldsCols = (endurance, personsOnBoard, radioUhf, radioVhf, radioElba, survivalEquipment, survivalEquipmentPolar, survivalEquipmentDesert, survivalEquipmentMaritime, survivalEquipmentJungle, jackets, jacketsLights, jacketsFluor, jacketsUhf, jacketsVhf, dinghies, dinghiesNumber, dinghiesCapacity)


  def dinghiesCover = column[Option[Boolean]]("bote_cubierto", O.Default(None))
  def dinghiesColor = column[Option[String]]("bote_color", O.Default(None))
  def aircraftColorAndMarkings = column[Option[String]]("aeronave_color_marca", O.Default(None))
  def remarks = column[Option[String]]("observacion_texto", O.Default(None))
  val supplementaryFields2Cols = (dinghiesCover, dinghiesColor, aircraftColorAndMarkings, remarks)


  def etd = column[DateTime]("fecha_etd", O.Default(new DateTime()))
  def eta = column[DateTime]("fecha_arribo", O.Default(new DateTime()))
  def atd = column[Option[String]]("hora_atd", O.Default(None))
  def ata = column[Option[String]]("hora_ata", O.Default(None))
  val timeFieldsCols = (etd, eta, atd, ata)


  def userId = column[Option[Int]]("usuario_id", O.Default(None))
  def pilotIdNumber = column[Option[String]]("piloto_rut", O.Default(None))
  def pilotName = column[Option[String]]("piloto_nombre", O.Default(None))
  def pilotSurname = column[Option[String]]("piloto_apellido_paterno", O.Default(None))
  def pilotOtherSurname = column[Option[String]]("piloto_apellido_materno", O.Default(None))
  def pilotLicense = column[Option[String]]("piloto_licencia", O.Default(None))
  def issuerIdNumber = column[Option[String]]("presentador_rut", O.Default(None))
  def issuerName = column[Option[String]]("presentador_nombre", O.Default(None))
  def issuerSurname = column[Option[String]]("presentador_apellido_paterno", O.Default(None))
  def issuerOtherSurname = column[Option[String]]("presentador_apellido_materno", O.Default(None))
  def issuerLicense = column[Option[String]]("presentador_licencia", O.Default(None))
  val userFieldsCols = (userId, pilotIdNumber, pilotName, pilotSurname, pilotOtherSurname, pilotLicense, issuerIdNumber, issuerName, issuerSurname, issuerOtherSurname, issuerLicense)


  def message = column[Option[String]]("mensaje", O.Default(None))
  def ci = column[Option[String]]("ci", O.Default(None))
  def csn = column[Option[Int]]("csn", O.Default(None))
  val messageFieldsCols = (message, ci, csn)

  def state = column[String]("estado", O.Default("1"))
  def bitCtrl = column[Int]("bit_ctrl", O.Default(0))
  def createdAt = column[Option[DateTime]]("created_at", O.Default(None))

  def aircraftExpirationDate = column[Option[String]]("fecha_vencimiento_aeronave", O.Default(None))
  def observation = column[Option[Boolean]]("observacion", O.Default(None))
  def issueDate = column[Option[DateTime]]("hora_presentacion", O.Default(None))
  def messageDate = column[Option[DateTime]]("fecha_mensaje", O.Default(None))
  val otherFieldsCols = (aircraftExpirationDate, observation, issueDate, messageDate)

  def fplId = column[Option[Int]]("fpl_id", O.Default(None))

  def * = (id.?, mainFieldsCols, mainFields2Cols, supplementaryFieldsCols, supplementaryFields2Cols, timeFieldsCols, userFieldsCols, messageFieldsCols, state, bitCtrl, createdAt, otherFieldsCols, fplId).shaped <> 
    ({
      case (id, mainFields, mainFields2, supplementaryFields, supplementaryFields2, timeFields, userFields, messageFields, state, bitCtrl, createdAt, otherFields, fplId) =>
      Fpl(id, MainFields.tupled.apply(mainFields), MainFields2.tupled.apply(mainFields2), SupplementaryFields.tupled.apply(supplementaryFields), SupplementaryFields2.tupled.apply(supplementaryFields2), TimeFields.tupled.apply(timeFields), UserFields.tupled.apply(userFields), MessageFields.tupled.apply(messageFields), state, bitCtrl, createdAt, OtherFields.tupled.apply(otherFields), fplId)
    }, {o: Fpl =>
      Some((o.id,MainFields.unapply(o.mainFields).get,MainFields2.unapply(o.mainFields2).get,SupplementaryFields.unapply(o.supplementaryFields).get,SupplementaryFields2.unapply(o.supplementaryFields2).get,TimeFields.unapply(o.timeFields).get,UserFields.unapply(o.userFields).get,MessageFields.unapply(o.messageFields).get,o.state,o.bitCtrl,o.createdAt,OtherFields.unapply(o.otherFields).get,o.fplId))
    })
}

class FplQueryBase extends BaseDAO[Fpl] {
  type DBTable = FplMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class FplFormData(obj: Fpl){
  def update(updatedObj: Fpl = obj)(implicit session: Session) = {

    FplQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Fpl)(implicit session: Session) = {
    val id = FplQuery.insert(insertedObj)

    id
  }
}

object FplForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "mainFields" -> mapping(
        "callsign" -> text,
        "flightRules" -> text,
        "typeOfFlight" -> optional(text),
        "number" -> optional(number),
        "typeOfAircraft" -> text,
        "wakeTurbulenceCategory" -> text,
        "radiocomEquipment" -> text,
        "survellianceEquipment" -> text,
        "departure" -> text,
        "etdHour" -> text,
        "speedUnit" -> text,
        "speed" -> text,
        "levelUnit" -> text,
        "level" -> text,
        "route" -> text,
        "destination" -> text,
        "eet" -> text
      )/*(MainFields.apply)(MainFields.unapply)*/
      ((callsign,flightRules,typeOfFlight,number,typeOfAircraft,wakeTurbulenceCategory,radiocomEquipment,survellianceEquipment,departure,etdHour,speedUnit,speed,levelUnit,level,route,destination,eet) => {
        MainFields(callsign, Some(""), flightRules, typeOfFlight, number, typeOfAircraft, wakeTurbulenceCategory, radiocomEquipment, survellianceEquipment, departure, etdHour, speedUnit, speed, levelUnit, level, route, destination, eet)
      })((formData: MainFields) => {
        Some((formData.callsign, formData.flightRules, formData.typeOfFlight, formData.number, formData.typeOfAircraft, formData.wakeTurbulenceCategory, formData.radiocomEquipment, formData.survellianceEquipment, formData.departure, formData.etdHour, formData.speedUnit, formData.speed, formData.levelUnit, formData.level, formData.route, formData.destination, formData.eet))
      }),
      "mainFields2" -> mapping(
        "alternative1" -> optional(text),
        "alternative2" -> optional(text),
        "otherInformation" -> text
      )/*(MainFields2.apply)(MainFields2.unapply)*/
      ((alternative1,alternative2,otherInformation) => {
        MainFields2(alternative1, alternative2, otherInformation)
      })((formData: MainFields2) => {
        Some((formData.alternative1, formData.alternative2, formData.otherInformation))
      }),
      "supplementaryFields" -> mapping(
        "endurance" -> optional(text),
        "personsOnBoard" -> optional(number),
        "radioUhf" -> optional(boolean),
        "radioVhf" -> optional(boolean),
        "radioElba" -> optional(boolean),
        "survivalEquipment" -> optional(boolean),
        "survivalEquipmentPolar" -> optional(boolean),
        "survivalEquipmentDesert" -> optional(boolean),
        "survivalEquipmentMaritime" -> optional(boolean),
        "survivalEquipmentJungle" -> optional(boolean),
        "jackets" -> optional(boolean),
        "jacketsLights" -> optional(boolean),
        "jacketsFluor" -> optional(boolean),
        "jacketsUhf" -> optional(boolean),
        "jacketsVhf" -> optional(boolean),
        "dinghies" -> optional(boolean),
        "dinghiesNumber" -> optional(number),
        "dinghiesCapacity" -> optional(number)
      )/*(SupplementaryFields.apply)(SupplementaryFields.unapply)*/
      ((endurance,personsOnBoard,radioUhf,radioVhf,radioElba,survivalEquipment,survivalEquipmentPolar,survivalEquipmentDesert,survivalEquipmentMaritime,survivalEquipmentJungle,jackets,jacketsLights,jacketsFluor,jacketsUhf,jacketsVhf,dinghies,dinghiesNumber,dinghiesCapacity) => {
        SupplementaryFields(endurance, personsOnBoard, radioUhf, radioVhf, radioElba, survivalEquipment, survivalEquipmentPolar, survivalEquipmentDesert, survivalEquipmentMaritime, survivalEquipmentJungle, jackets, jacketsLights, jacketsFluor, jacketsUhf, jacketsVhf, dinghies, dinghiesNumber, dinghiesCapacity)
      })((formData: SupplementaryFields) => {
        Some((formData.endurance, formData.personsOnBoard, formData.radioUhf, formData.radioVhf, formData.radioElba, formData.survivalEquipment, formData.survivalEquipmentPolar, formData.survivalEquipmentDesert, formData.survivalEquipmentMaritime, formData.survivalEquipmentJungle, formData.jackets, formData.jacketsLights, formData.jacketsFluor, formData.jacketsUhf, formData.jacketsVhf, formData.dinghies, formData.dinghiesNumber, formData.dinghiesCapacity))
      }),
      "supplementaryFields2" -> mapping(
        "dinghiesCover" -> optional(boolean),
        "dinghiesColor" -> optional(text),
        "aircraftColorAndMarkings" -> optional(text),
        "remarks" -> optional(text)
      )/*(SupplementaryFields2.apply)(SupplementaryFields2.unapply)*/
      ((dinghiesCover,dinghiesColor,aircraftColorAndMarkings,remarks) => {
        SupplementaryFields2(dinghiesCover, dinghiesColor, aircraftColorAndMarkings, remarks)
      })((formData: SupplementaryFields2) => {
        Some((formData.dinghiesCover, formData.dinghiesColor, formData.aircraftColorAndMarkings, formData.remarks))
      })
    )/*(Fpl.apply)(Fpl.unapply)*/
    ((id,mainFields,mainFields2,supplementaryFields,supplementaryFields2) => {
      FplFormData(Fpl(id, mainFields, mainFields2, supplementaryFields, supplementaryFields2, TimeFields(), UserFields(), MessageFields(), "1", 0, Some(new DateTime()), OtherFields(), Some(0)))
    })((formData: FplFormData) => {
      Some((formData.obj.id, formData.obj.mainFields, formData.obj.mainFields2, formData.obj.supplementaryFields, formData.obj.supplementaryFields2))
    })
  )
}