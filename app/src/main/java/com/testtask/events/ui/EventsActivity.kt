package com.testtask.events.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import com.testtask.events.R
import com.testtask.events.databinding.ActivityEventsBinding
import com.testtask.events.dependency.Dependencies

class EventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventsBinding

    private val eventsAdapter = EventsAdapter(emptyList())

    private val viewModel: EventsViewModel by viewModels {
        EventsViewModelFactory(
            Dependencies.eventsRepository,
            Dependencies.eventsMapper
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_events)

        binding.apply {
            lifecycleOwner = this@EventsActivity
            viewmodel = viewModel

            refreshLayout.apply {
                setOnRefreshListener { updateData() }
                setColorSchemeResources(
                    android.R.color.holo_blue_dark,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )
            }

            recyclerView.apply {
                setHasFixedSize(true)
                adapter = eventsAdapter.apply {
                    setOnItemClickListener {
                        openCustomTub(it.redirectUrl)
                    }
                }
            }
        }

        viewModel.apply {
            eventsList.observe(this@EventsActivity) { items ->
                eventsAdapter.items = items
                eventsAdapter.notifyDataSetChanged()
            }
            error.observe(this@EventsActivity) { errorMessage ->
                Toast.makeText(this@EventsActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun updateData() {
        viewModel.updateEvents()
    }

    private fun openCustomTub(url: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(this, Uri.parse(url))
    }

}