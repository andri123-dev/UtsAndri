package id.ac.polbeng.andrisaputra.utsandri.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.ac.polbeng.andrisaputra.utsandri.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load a profile image (could be local or remote)
        // For demonstration, using a placeholder or a known URL
        Glide.with(this)
            .load("https://avatars.githubusercontent.com/u/1?v=4") // Placeholder developer image
            .circleCrop()
            .into(binding.ivProfile)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
