package org.tlauncher.tlauncherpe.mc.presentationlayer

import io.reactivex.Completable
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.ZipParameters
import java.io.File


fun zipManager(files: MutableList<String>, zipFile: String, json: String): Completable =
        Completable.create {
            try {
                for (i in files.indices) {
                    val zipF = ZipFile(zipFile)
                    val f = File(files[i])
                    if (f.isFile) {
                        zipF.addFile(f, ZipParameters())
                    } else {
                        zipF.addFolder(files[i],ZipParameters())
                    }
                }
            } catch (e: ZipException) {
                e.printStackTrace()
            }
            /*var origin: BufferedInputStream?
            val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile)))
            out.use { out ->
                val data = ByteArray(2048)
                val zipF = ZipFile(zipFile)
                for (i in files.indices) {
                    val f = File(files[i])
                    if (f.isFile) {
                        val fi = FileInputStream(files[i])
                        origin = BufferedInputStream(fi, 2048)
                        try {
                            val entry = ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1))
                            out.putNextEntry(entry)
                            var count: Int
                            count = origin!!.read(data, 0, 2048)
                            while (count != -1) {
                                out.write(data, 0, count)
                                count = origin?.read(data, 0, 2048) ?: 0
                            }
                        } finally {
                            origin?.close()
                        }
                    }else{
                        try {
                            zipF.extractAll(files[i])
                        } catch (e: ZipException) {
                            e.printStackTrace()
                        }
                    }
                }
            }*/
            val json = File(json)
            json.delete()
        }


