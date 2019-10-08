package com.example.tokyoartbeat

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayInputStream

class NewsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_detail)
        val photoEvent = findViewById<ImageView>(R.id.photoEvent)
        var venue = findViewById<TextView>(R.id.idVenue)
        var nameEvent = findViewById<TextView>(R.id.idNameEvent)
        var date_n_count = findViewById<TextView>(R.id.idDateCount)
        var content = findViewById<TextView>(R.id.idContent)
        var contact = findViewById<TextView>(R.id.idContact)
        var address = findViewById<TextView>(R.id.idAddress)
        var category = findViewById<TextView>(R.id.idCategory)
        var photo = intent.getByteArrayExtra("photo")
        val imageStream = ByteArrayInputStream(photo)
        val theImage = BitmapFactory.decodeStream(imageStream)
        photoEvent.setImageBitmap(theImage)
        venue.text = intent.getStringExtra("nameVenue")

        nameEvent.text = intent.getStringExtra("nameEvent")
        var startDate = intent.getStringExtra("startDate")
        var endDate = intent.getStringExtra("endDate")
        if(startDate.isNullOrBlank()){
            date_n_count.text = endDate + " - " + intent.getStringExtra("going_count") + " người tham gia"
        } else {
            date_n_count.text = startDate + " - " + intent.getStringExtra("going_count") + " người tham gia"
        }
        content.text = intent.getStringExtra("description")
        makeTextViewResizable(content, 3, "View More", true)
        contact.text = intent.getStringExtra("artist")
        category.text = intent.getStringExtra("category")
        address.text = intent.getStringExtra("address")
    }
    fun makeTextViewResizable(tv: TextView, maxLine: Int, expandText: String, viewMore: Boolean) {

        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                val text: String
                val lineEndIndex: Int
                val obs = tv.viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                if (maxLine == 0) {
                    lineEndIndex = tv.layout.getLineEnd(0)
                    text = tv.text.subSequence(
                        0,
                        lineEndIndex - expandText.length + 1
                    ).toString() + " " + expandText
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    text = tv.text.subSequence(
                        0,
                        lineEndIndex - expandText.length + 1
                    ).toString() + " " + expandText
                } else {
                    lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                }
                tv.text = text
                tv.movementMethod = LinkMovementMethod.getInstance()
                tv.setText(
                    addClickablePartTextViewResizable(
                        Html.fromHtml(tv.text.toString()), tv, lineEndIndex, expandText,
                        viewMore
                    ), TextView.BufferType.SPANNABLE
                )
            }
        })

    }

    private fun addClickablePartTextViewResizable(
        strSpanned: Spanned, tv: TextView,
        maxLine: Int, spanableText: String, viewMore: Boolean
    ): SpannableStringBuilder {
        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)

        if (str.contains(spanableText)) {
            ssb.setSpan(object : ClickableSpan() {

                override fun onClick(widget: View) {
                    tv.layoutParams = tv.layoutParams
                    tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                    tv.invalidate()
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false)
                    } else {
                        makeTextViewResizable(tv, 3, "View More", true)
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)

        }
        return ssb
    }
}