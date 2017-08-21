package org.tlauncher.tlauncherpe.mc.datalayer.db.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type

open class MySkinsEntity: RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
    open var type: MySkinsTypeEntity = MySkinsTypeEntity()
    open var category: MySkinsCategoryEntity = MySkinsCategoryEntity()
    open var text: String = ""
    open var date: String = ""
    open var views: String = ""
    open var downloads: String = ""
    open var file_url: String = ""
    open var file_size: String = ""
    open var imgs: RealmList<MySkinsImgEntity> = RealmList()
    //open var versions: Versions = null!!
    open var tags: RealmList<MySkinsTagEntity> = RealmList()
    open var filePath: String = ""
    open var isImported: Boolean = false

}

open class MySkinsImgEntity : RealmObject() {
    open var img: String = ""
}

open class MySkinsTagEntity : RealmObject() {
    open var tag: String = ""
}

open class MySkinsTypeEntity(): RealmObject(){

    constructor(type: Type?) : this() {
        id = type?.id ?: 0
        name = type?.name ?: ""
    }

    open var id: Int = 0
    open var name: String = ""
}

open class MySkinsCategoryEntity(): RealmObject() {

    constructor(category: Category?) : this() {
        id = category?.id ?: ""
        name = category?.name ?: ""
    }

    open var id: String = ""
    open var name: String = ""
}