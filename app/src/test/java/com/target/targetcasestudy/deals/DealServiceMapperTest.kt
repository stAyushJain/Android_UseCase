package com.target.targetcasestudy.deals

import com.target.targetcasestudy.deals.data.DealsServiceMapper
import com.target.targetcasestudy.deals.data.model.DealsDTO
import com.target.targetcasestudy.deals.data.model.Product
import com.target.targetcasestudy.deals.data.model.RegularPrice
import com.target.targetcasestudy.deals.domain.model.DealModel
import org.junit.Test

class DealServiceMapperTest {

    @Test
    fun `check correct mapped values`() {
        val expectedResult = DealModel(
                id = "0",
                title = "Product 1",
                description = "This is product number 1",
                price = "$10.99",
                aisle = "a1",
                imageUrl = "Http://my_url"
        )
        val apiResponse = DealsDTO(
                listOf(Product(
                        id = "0",
                        title = "Product 1",
                        description = "This is product number 1",
                        regularPrice = RegularPrice(10,"$","$10.99"),
                        aisle = "a1",
                        imageUrl = "Http://my_url"
                ))
        )
        val mapper = DealsServiceMapper()
        assert(mapper.mapToDomainList(apiResponse).get(0) == expectedResult)
    }
}