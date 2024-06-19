package com.example.kotlinback.global

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.util.StringUtils
import java.util.ArrayList

@Converter
class BooleanToYNConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean?): String = if (attribute != null && attribute) "Y" else "N"

    override fun convertToEntityAttribute(dbData: String): Boolean = ("Y" ==  dbData)
}

@Converter
class StrListToStrConverter : AttributeConverter<List<String>?, String> {
    override fun convertToDatabaseColumn(strList: List<String>?): String {
        return strList?.joinToString(separator = SPLIT_CHAR) ?: ""
    }

    override fun convertToEntityAttribute(str: String): List<String> {
        return if (!StringUtils.hasText(str)) ArrayList() else str.split(SPLIT_CHAR)
    }

    companion object {
        const val SPLIT_CHAR = ";"
    }
}
