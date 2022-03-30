package com.pbt.myfarm.Activity.TaskFunctions

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class ProgressRequestBody(file: File, content_type: String?) :
//class ProgressRequestBody(file: File, content_type: String?, listener: UploadCallbacks) :
    RequestBody() {

    private var mFile: File? = file
    private var content_type: String? = content_type
    private val mPath: String? = null
    public var mListener: UploadCallbacks? = null

    override fun contentType(): okhttp3.MediaType? {
        return (content_type+"/*").toMediaTypeOrNull();
    }

//    fun ProgressRequestBody(file: File, content_type: String?, listener: UploadCallbacks) {
//        this.content_type = content_type
//        mFile = file
//        mListener = listener
//    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {

        val fileLength = mFile!!.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = FileInputStream(mFile)
        var uploaded: Long = 0

        try {
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (`in`.read(buffer).also { read = it } != -1) {

                // update progress on UI thread
//              ProgressUpdater(uploaded, fileLength)
                mListener?.onProgressUpdate((100 * uploaded / fileLength).toInt())
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
            }
        } finally {
            `in`.close()
        }
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile!!.length()
    }

    interface UploadCallbacks {
        fun onProgressUpdate(percentage: Int)
        fun onError()
        fun onFinish()
    }


    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }}