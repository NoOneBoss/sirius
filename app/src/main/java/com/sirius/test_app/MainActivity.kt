package com.sirius.test_app

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val dataModel = DataModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleImageView = findViewById<ImageView>(R.id.titleImage)
        DownloadImageFromInternet(titleImageView).execute("https://i.ibb.co/g3YVWD2/img-background.png")

        val logoImageView = findViewById<ImageView>(R.id.logoImage)
        DownloadImageFromInternet(logoImageView).execute("https://i.ibb.co/Pjf2B69/img-logo.png")

        val titleName = findViewById<TextView>(R.id.titleName)
        titleName.text = dataModel.name

        val reviewsStars = findViewById<RatingBar>(R.id.smallRating)
        reviewsStars.rating = dataModel.rating
        val reviewsSmall = findViewById<TextView>(R.id.reviewsSmall)
        reviewsSmall.text = dataModel.gradeCnt

        val firstTag = findViewById<TextView>(R.id.firstTag)
        firstTag.text = dataModel.tags[0]
        val secondTag = findViewById<TextView>(R.id.secondTag)
        secondTag.text = dataModel.tags[1]
        val thirdTag = findViewById<TextView>(R.id.thirdTag)
        thirdTag.text = dataModel.tags[2]

        val description = findViewById<TextView>(R.id.descriptionText)
        description.text = dataModel.description

        val bigRating = findViewById<TextView>(R.id.bigRating)
        bigRating.text = dataModel.rating.toString()
        val reviewsStars2 = findViewById<RatingBar>(R.id.smallRating2)
        reviewsStars2.rating = dataModel.rating
        val reviewsSmall2 = findViewById<TextView>(R.id.reviewsSmall2)
        reviewsSmall2.text = "${dataModel.gradeCnt} Reviews"

        val ratingsRecycler = findViewById<RecyclerView>(R.id.recycler)
        val adapter = RatingAdapter(this, dataModel.reviews)
        ratingsRecycler.adapter = adapter
    }

    private inner class RatingAdapter constructor(context: Context?, private val items: List<ReviewModel>) :
        RecyclerView.Adapter<RatingAdapter.ViewHolder>() {
        private val inflater: LayoutInflater
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = inflater.inflate(R.layout.review_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            DownloadImageFromInternet(holder.image).execute(item.userImage/*"https://i.ibb.co/Pjf2B69/img-logo.png"*/)
            holder.username.text = item.userName
            holder.date.text = item.date
            holder.review.text = item.message
        }

        override fun getItemCount(): Int {
            return items.size
        }

        inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.image)
            val username: TextView = view.findViewById(R.id.username)
            val date: TextView = view.findViewById(R.id.date)
            val review: TextView = view.findViewById(R.id.review)
        }

        init {
            inflater = LayoutInflater.from(context)
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val input = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(input)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}