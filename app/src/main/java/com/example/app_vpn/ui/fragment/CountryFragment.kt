package com.example.app_vpn.ui.fragment

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.app_vpn.R
import com.example.app_vpn.data.local.Country
import com.example.app_vpn.ui.custom.CustomArrayCountryAdapter
import java.util.Locale

class CountryFragment : Fragment() {
    private lateinit var customArrayCountryAdapter: CustomArrayCountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Truy cập AssetManager từ context
        val assetManager: AssetManager = requireContext().assets

        // Tạo một danh sách rỗng để chứa các đối tượng quốc gia
        val list = mutableListOf<Country>()

        // Lấy danh sách tên tất cả các tệp trong thư mục assets/Flags
        val flagFiles = assetManager.list("Flags") ?: emptyArray()

        // Duyệt qua danh sách các tệp và thêm chúng vào danh sách quốc gia
        for (fileName in flagFiles) {
            // Lấy hình ảnh từ thư mục assets/Flags
            val flagInputStream = assetManager.open("Flags/$fileName")
            val flagDrawable = Drawable.createFromStream(flagInputStream, null)

            // Lấy tên quốc gia từ tên tệp (giả sử tên tệp có dạng "country_flag.jpg")
            val countryName = fileName.substringBefore("_flag")
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

            // Thêm quốc gia vào danh sách
            list.add(Country(flagDrawable, countryName, "50ms")) // Thêm thời gian ping mặc định

            // Đóng luồng đầu vào
            flagInputStream.close()
        }

        customArrayCountryAdapter = CustomArrayCountryAdapter(requireContext(), list)

        // Inflate layout cho fragment này
        val view = inflater.inflate(R.layout.fragment_country, container, false)

        // Gắn adapter với ListView trong layout
        val listView = view.findViewById<ListView>(R.id.list)
        listView.adapter = customArrayCountryAdapter

        return view
    }
}