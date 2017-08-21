package org.tlauncher.tlauncherpe.mc.datalayer.db.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ServersEntity : RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
    open var text: String = ""
    open var ip: String = ""
    open var rank: Int = 0
    open var versions: ServersVersionsEntity = ServersVersionsEntity()
}

open class ServersVersionsEntity: RealmObject() {
    open var from: ServersFromToEntity = ServersFromToEntity()
    open var to: ServersFromToEntity = ServersFromToEntity()
}

open class ServersFromToEntity: RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
}