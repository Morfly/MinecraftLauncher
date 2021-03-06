package org.tlauncher.tlauncherpe.mc.presentationlayer.addon

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

fun unzipM(_zipFile: String, _targetLocation: String): Observable<MutableList<String>> =
        Observable.fromCallable {
            val r: MutableList<String> = mutableListOf()
            try {
                val fin = FileInputStream(_zipFile)
                val zin = ZipInputStream(fin)
                var ze: ZipEntry? = zin.nextEntry
                val mFolder: List<String> = ze?.name?.split("/") ?: emptyList()
                //r = zipModel(id, _targetLocation + mFolder[0], mFolder[0])
                val f = File(_targetLocation + mFolder[0])
                if (f.exists()) {

                } else {
                    if (f.isDirectory) {
                        f.mkdirs()
                    }
                }

                while (ze != null) {
                    if (ze.isDirectory) {
                        val f = File(_targetLocation + ze.name)
                        if (f.exists()) {

                        } else {
                            if (!f.isDirectory) {
                                f.mkdirs()
                            }
                        }
                    } else {
                        val mFolder: List<String> = ze.name.split("/")
                        if (mFolder.size == 3) {
                            val f = File(_targetLocation + mFolder[0] + "/" + mFolder[1])

                            if (f.exists()) {

                            } else {
                                if (!f.isDirectory) {
                                    f.mkdirs()
                                }
                            }
                        }
                        r.add(_targetLocation + ze.name)
                        val fout = FileOutputStream(_targetLocation + ze.name)
                        var size: Int
                        val buffer = ByteArray(2048)
                        val bufferOut: BufferedOutputStream = BufferedOutputStream(fout, buffer.size)
                        size = zin.read(buffer, 0, buffer.size)
                        while (size != -1) {
                            bufferOut.write(buffer, 0, size)
                            size = zin.read(buffer, 0, buffer.size)
                        }
                        bufferOut.flush()
                        bufferOut.close()
                        zin.closeEntry()
                        fout.close()
                    }
                    ze = zin.nextEntry
                }
                zin.close()

            } catch (e: Exception) {
                println(e)
            }
            return@fromCallable r
        }.subscribeOn(Schedulers.io())