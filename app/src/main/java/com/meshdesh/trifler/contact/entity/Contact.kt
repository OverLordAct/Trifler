package com.meshdesh.trifler.contact.entity

data class Contact(
    val status: Boolean,
    val contactData: ContactData
) {
    data class ContactData(
        val addedOn: String,
        val name: String
    )
}
