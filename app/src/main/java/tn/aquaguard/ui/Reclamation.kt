package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.R
import tn.aquaguard.adapters.MessageAdapter

class Reclamation : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamation)
        val text = intent.getStringExtra("TEXT")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = "reclamation title"
        recyclerView = findViewById(R.id.recyclerViewMessages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageList = mutableListOf(text ?: "")
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter

        // Notify the adapter that the data set has changed
        messageAdapter.notifyDataSetChanged()
    }
}