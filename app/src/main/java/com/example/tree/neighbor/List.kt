package com.example.tree.neighbor


class nlist(var id: Long, var name: String, var followerCount: Int,var followingCount: Int,var treeCount: Int,var treeLevel: Int)

class neighborList {
    val NeighborList = ArrayList<nlist>()

    fun AddFriend(list: nlist) {
        NeighborList.add(list)
    }
}