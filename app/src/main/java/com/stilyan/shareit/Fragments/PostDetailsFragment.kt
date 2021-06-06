package com.stilyan.shareit.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stilyan.shareit.Adapter.PostAdapter
import com.stilyan.shareit.Model.Post
import com.stilyan.shareit.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PostDetailsFragment : Fragment() {
    private var postAdapter: PostAdapter? = null
    private var postList: MutableList<Post>? = null
    private var postId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_post_details, container, false)


        val preference = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if(preference != null){
            postId = preference.getString("postId", "none").toString()
        }


        var recyclerView: RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_post_details)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        postList = ArrayList()
        postAdapter = context?.let { PostAdapter(it, postList as ArrayList<Post>) }
        recyclerView.adapter = postAdapter


        retrievePosts()
        return view
    }




    private fun retrievePosts() {
        val postsRef = FirebaseDatabase.getInstance().reference
            .child("Posts")
            .child(postId)

        postsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                postList?.clear()
                val post = p0.getValue(Post::class.java)
                postList!!.add(post!!)
                postAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}