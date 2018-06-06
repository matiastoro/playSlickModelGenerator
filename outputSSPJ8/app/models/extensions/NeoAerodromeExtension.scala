package models.extensions

import models._

trait NeoAerodromeExtension{ this: NeoAerodrome =>

}

object NeoAerodromePartition{
import models.NeoAerodromePartition._
trait GeneralInformationExtension{ this: GeneralInformation =>

}

trait AerodromeDescriptionExtension{ this: AerodromeDescription =>

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

trait NeoAerodromeQuery{
  this: DatabaseClient[NeoAerodrome] =>
}