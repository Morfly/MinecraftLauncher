package org.tlauncher.tlauncherpe.mc

import android.content.Context
import android.os.Environment
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


fun JsonFileReader(path: String, context: Context, pack: String, version: String){
    var json: String? = null
    try {
        val fis = FileInputStream(path)
        val `is` = fis
        val buffer = ByteArray(`is`.available())
        `is`.read(buffer)
        `is`.close()

        json = String(buffer, Charset.forName("UTF-8"))
        val obj = JSONObject(json)
        val uuid = (obj.get("header") as JSONObject).get(pack)
        val version = (obj.get("header") as JSONObject).get(version).toString()
        var output: Writer? = null
        val file = File(Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.path_to_import)+"global_resource_packs.json")
        output = BufferedWriter(FileWriter(file))
        output.write(
                "{\"pack_id\":\"" + uuid + "\"," +
                        "\"version\":" + version +
                        "}"
        )
        output.close()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
}