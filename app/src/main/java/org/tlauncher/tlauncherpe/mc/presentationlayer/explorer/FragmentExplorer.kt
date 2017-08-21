package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer

import android.os.Bundle
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.BaseFragment
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.FrgamentExplorerBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.adapter.ExplorerAdapter
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.adapter.ItemExplorer
import org.tlauncher.tlauncherpe.mc.presentationlayer.zipManager
import java.io.File
import java.io.FileWriter



class FragmentExplorer(val chackStrings: MutableList<String>, val array: String,
                       val dateObj: String, val type: String, var path: String) :
        BaseFragment<ExplorerContract.Presenter,FrgamentExplorerBinding>(), ExplorerContract.View {

    fun getFoldersList(): List<ItemExplorer> {
        val dir: File = File(path)
        val names = dir.list()
        val data = names.map { ItemExplorer(it) }
        return data
    }

    override fun getViewModelBindingId() = BR.viewModel

    override fun getLayoutId(): Int = R.layout.frgament_explorer

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.presenter = presenter
        binding.packagesList.layoutManager = LinearLayoutManager(context)
        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(context, R.drawable.list_divider))
        binding.packagesList.addItemDecoration(decorator)
        binding.packagesList.adapter = ExplorerAdapter(getFoldersList(), presenter, activity, path/*, this*/)
        binding.buttonSave.setOnClickListener {
            when (type){
                "addon"->{
                    val mypath = FileWriter(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_addons) + "AddonDataJson.json")
                    mypath.write(array)
                    mypath.flush()
                    mypath.close()
                    chackStrings.add(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_addons) + "AddonDataJson.json")
                    zipManager(chackStrings, path + "/" + "addons-all-" + dateObj +".zip"
                            ,Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_addons) + "AddonDataJson.json")
                            .applyApiRequestSchedulers()
                            .subscribe{}
                    activity.finish()
                }
                "sid"->{
                    val mypath = FileWriter(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_sids) + "SidDataJson.json")
                    mypath.write(array)
                    mypath.flush()
                    mypath.close()
                    chackStrings.add(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_sids) + "SidDataJson.json")
                    zipManager(chackStrings, "$path/sids-all-$dateObj.zip"
                            ,Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_sids) + "SidDataJson.json")
                            .applyApiRequestSchedulers()
                            .subscribe{}
                    activity.finish()
                }
                "skin"->{
                    val mypath = FileWriter(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_skins) + "SkinDataJson.json")
                    mypath.write(array)
                    mypath.flush()
                    mypath.close()
                    chackStrings.add(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_skins) + "SkinDataJson.json")
                    zipManager(chackStrings, "$path/skins-all-$dateObj.zip"
                            ,Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_skins) + "SkinDataJson.json")
                            .applyApiRequestSchedulers()
                            .subscribe{}
                    activity.finish()
                }
                "texture"->{
                    val mypath = FileWriter(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_textures) + "TextureDataJson.json")
                    mypath.write(array)
                    mypath.flush()
                    mypath.close()
                    chackStrings.add(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_textures) + "TextureDataJson.json")
                    zipManager(chackStrings, "$path/textures-all-$dateObj.zip"
                            ,Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_textures) + "TextureDataJson.json")
                            .applyApiRequestSchedulers()
                            .subscribe{}
                    activity.finish()
                }
                "world"->{
                    val mypath = FileWriter(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_maps) + "WorldDataJson.json")
                    mypath.write(array)
                    mypath.flush()
                    mypath.close()
                    chackStrings.add(Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_maps) + "WorldDataJson.json")
                    zipManager(chackStrings, "$path/maps-all-$dateObj.zip"
                            ,Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.backup_maps) + "WorldDataJson.json")
                            .applyApiRequestSchedulers()
                            .subscribe{}
                    activity.finish()
                }
            }
        }
        binding.button2.setOnClickListener {
            activity.finish()
        }
    }

    override fun inject() {
        (activity as ExplorerActivity)
                .getComponents()
                .plusExplorerComponent()
                .build()
                .inject(this)
    }

    override fun returnPath(path: String) {
        (activity as ExplorerActivity).openThree(path)
    }
}
