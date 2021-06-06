package com.stilyan.shareit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.stilyan.shareit.Model.Comment
import com.stilyan.shareit.Model.User
import com.stilyan.shareit.R
import de.hdodenhof.circleimageview.CircleImageView

class CommentsAdapter(private val mContext: Context,
                      private val mComment: MutableList<Comment>?
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>()
{
    //връзка към Firebase сървър
    private var firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.comments_item_layout, parent, false)
        return ViewHolder(view)
    }

    //взимане на общ брой публикации от последователи
    override fun getItemCount(): Int {
        return mComment!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //взимане на информация относно портебиреля от Firebase сървър
        firebaseUser = FirebaseAuth.getInstance().currentUser

        val comment = mComment!![position]
        holder.commentTV.text = comment.getComment()
        getUserInfo(holder.imageProfile, holder.userNameTV, comment.getPublisher())
    }



    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView){
        //профилна снимка
        var imageProfile: CircleImageView
        //потребителско име
        var userNameTV: TextView
        //коментар
        var commentTV: TextView

        init{
            //наследяване на снимка, потребителско име и коментари от дизайн страниците
            imageProfile = itemView.findViewById(R.id.user_profile_image_comment)
            userNameTV = itemView.findViewById(R.id.user_name_comment)
            commentTV = itemView.findViewById(R.id.comment_comment)
        }
    }

    //връзване към Firebase сървър и изтегляне на информацията от него (раздел Users)
    private fun getUserInfo(imageProfile: CircleImageView, userNameTV: TextView, publisher: String) {
        val userREF = FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(publisher)

        //взимане на профилна снимка и потребителско име
        userREF.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user = p0.getValue(User::class.java)
                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(imageProfile)
                    userNameTV.text = user!!.getUsername()
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

}