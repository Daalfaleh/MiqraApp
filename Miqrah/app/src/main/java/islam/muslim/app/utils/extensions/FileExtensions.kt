package islam.muslim.app.utils.extensions

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

object FileExtensions {

    fun Context.isFileDownloaded(directory: String, filename: String): Boolean {
        val mydir = this.getDir(directory, Context.MODE_PRIVATE)
        val file = File(mydir, filename)
        return file.exists()
    }

    fun File.getNamesFileList(): List<String> {
        val inFiles = ArrayList<String>()
        try {
            val files = this.listFiles()?.sortedArray()
            files?.forEach { println("filename $it") }
            if (!files.isNullOrEmpty()) {
                for (file in files) {
                    if (!file.isDirectory) {
                        inFiles.add(file.name)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return inFiles
    }

    fun File.getFileContent():String{
        val text = StringBuilder()

        try {
            val br = BufferedReader(FileReader(this))
            var line: String?
            while (br.readLine().also { line = it } != null) {
                text.append(line)
                text.append('\n')
            }
            br.close()
        } catch (e: IOException) {
            //You'll need to add proper error handling here
        }
        return text.toString()
    }
}