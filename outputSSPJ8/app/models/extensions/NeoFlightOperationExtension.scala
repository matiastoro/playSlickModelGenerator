package models.extensions

import models._

trait NeoFlightOperationExtension{ this: NeoFlightOperation =>

}

object NeoFlightOperationPartition{
import models.NeoFlightOperationPartition._
trait GeneralInformationExtension{ this: GeneralInformation =>

}

trait AircraftIdentificationExtension{ this: AircraftIdentification =>

}

trait AircraftDescriptionExtension{ this: AircraftDescription =>

}

trait FlightDetailsExtension{ this: FlightDetails =>

}

trait OperationalInformationExtension{ this: OperationalInformation =>

}

trait ClassificationExtension{ this: Classification =>

}

trait RiskExtension{ this: Risk =>

}

trait AssessmentExtension{ this: Assessment =>

}

trait ReportManagementExtension{ this: ReportManagement =>

}
}

trait NeoFlightOperationQuery{
  this: DatabaseClient[NeoFlightOperation] =>
}