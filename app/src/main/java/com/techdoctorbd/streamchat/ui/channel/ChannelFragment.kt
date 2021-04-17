package com.techdoctorbd.streamchat.ui.channel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.techdoctorbd.streamchat.databinding.FragmentChannelBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User

class ChannelFragment : Fragment() {

    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!
    private val args: ChannelFragmentArgs by navArgs()
    private val client = ChatClient.instance()
    private lateinit var user: User
    private val TAG = "ChannelFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChannelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUser()
    }

    private fun setupUser() {
        if (client.getCurrentUser() == null) {
            user = if (args.chatUser.username == "Abu Raihan") {
                User(
                    id = args.chatUser.username,
                    extraData = mutableMapOf(
                        "name" to args.chatUser.firstName,
                        "country" to "Bangladesh",
                        "image" to "https://avatars.githubusercontent.com/u/44436797?v=4"
                    )
                )
            } else {
                User(
                    id = args.chatUser.username,
                    extraData = mutableMapOf(
                        "name" to args.chatUser.firstName
                    )
                )
            }

            val token = client.devToken(user.id)
            client.connectUser(user, token).enqueue {
                if (it.isSuccess) {
                    Log.d(TAG, "Success connecting the user")
                } else {
                    Log.d(TAG, it.isError.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}