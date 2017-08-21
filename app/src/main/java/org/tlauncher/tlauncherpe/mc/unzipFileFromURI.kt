package org.tlauncher.tlauncherpe.mc

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class unzipFileFromURI(var path: String, var zipName: String) {
    internal var `is`: InputStream
    internal var zis: ZipInputStream
    init {
        var filename: String
        `is` = FileInputStream(path + zipName)
        zis = ZipInputStream(BufferedInputStream(`is`))
        var ze: ZipEntry
        val buffer = ByteArray(1024)
        var count: Int
        ze = zis.nextEntry
        while (ze != null) {
            //ze = zis.nextEntry
            filename = ze.name
            if (ze.isDirectory) {
                val fmd = File(path + filename)
                fmd.mkdirs()
                continue
            }
            val fout = FileOutputStream(path + filename)
            count = zis.read(buffer)
            while ((count) != -1) {
                count = zis.read(buffer)
                fout.write(buffer, 0, count)
            }

            fout.close()
            zis.closeEntry()
        }
        zis.close()
    }
    internal var e: IOException? = null
    init {
        e!!.printStackTrace()
        //return false
    }
    //return true
}
