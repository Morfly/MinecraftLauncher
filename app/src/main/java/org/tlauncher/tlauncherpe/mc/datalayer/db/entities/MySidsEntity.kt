package org.tlauncher.tlauncherpe.mc.datalayer.db.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type

open class MySidsEntity: RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
    open var type: MySidsTypeEntity = MySidsTypeEntity()
    open var category: MySidsCategoryEntity = MySidsCategoryEntity()
    open var text: String = ""
    open var date: String = ""
    open var views: String = ""
    open var downloads: String = ""
    open var file_url: String = ""
    open var file_size: String = ""
    open var imgs: RealmList<MySidsImgEntity> = RealmList()
    //open var versions: Versions = null!!
    open var tags: RealmList<MySidsTagEntity> = RealmList()
    open var filePath: String = ""

}

open class MySidsImgEntity : RealmObject() {
    open var img: String = ""
}

open class MySidsTagEntity : RealmObject() {
    open var tag: String = ""
}

open class MySidsTypeEntity(): RealmObject(){

    constructor(type: Type?) : this() {
        id = type?.id ?: 0
        name = type?.name ?: ""
    }

    open var id: Int = 0
    open var name: String = ""
}

open class MySidsCategoryEntity(): RealmObject() {

    constructor(category: Category?) : this() {
        id = category?.id ?: ""
        name = category?.name ?: ""
    }

    open var id: String = ""
    open var name: String = ""
}