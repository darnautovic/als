package controllers.api.download

import com.als.module.registry.ServiceRegistry
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits._


object DownloadController extends Controller {

  private val licenseService = ServiceRegistry.licenceService


  def download(id :Long) = Action {
    import play.api.libs.iteratee._
    import java.util.zip._

    val licence =  licenseService.getById(id)

    val r = new java.util.Random()

    val enumerator = Enumerator.outputStream { os =>
      val zip = new ZipOutputStream(os);
      zip.putNextEntry(new ZipEntry("test-zip/LICENCE_hash.txt"))
      zip.write(licence.get.licenceHash.getBytes())
      zip.closeEntry()

      zip.putNextEntry(new ZipEntry("test-zip/LICENCE_signed_hash.txt"))
      zip.write(licence.get.signedHash.getBytes())
      zip.closeEntry()

      zip.close()

    }
    Ok.chunked(enumerator >>> Enumerator.eof).withHeaders(
      "Content-Type"->"application/zip",
      "Content-Disposition"->"attachment; filename=test.zip"
    )
  }
}