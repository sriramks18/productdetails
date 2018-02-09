package com.myretail.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Item {
  String tcin
  String dpci
  String upc

  @JsonProperty("product_description")
  ProductDescription product_description

  @JsonProperty("parent_items")
  String parentItems
}

/*

{
  "tcin": "13860428",
  "bundle_components": {
    "is_assortment": false,
    "is_kit_master": false,
    "is_standard_item": true,
    "is_component": false
  },
  "dpci": "058-34-0436",
  "upc": "025192110306",
  "product_description": {
    "title": "The Big Lebowski (Blu-ray)",
    "bullet_description": [
      "<B>Movie Genre:</B> Comedy",
      "<B>Software Format:</B> Blu-ray",
      "<B>Movie Studio:</B> Universal Studios"
    ],
    "general_description": "Blu-ray BIG LEBOWSKI, THE Movies"
  },
  "parent_items": "46767107",
  "buy_url": "https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428",
  "variation": {},
  "enrichment": {
    "images": [
      {
        "base_url": "https://target.scene7.com/is/image/Target/",
        "primary": "13860428"
      }
    ],
    "sales_classification_nodes": [
      {
        "node_id": "5xswx"
      },
      {
        "node_id": "yzuww"
      },
      {
        "node_id": "55ayu"
      },
      {
        "node_id": "5t0ak"
      }
    ]
  },
  "return_method": "This item can be returned to any Target store or Target.com.",
  "handling": {},
  "recall_compliance": {
    "is_product_recalled": false
  },
  "tax_category": {
    "tax_class": "G",
    "tax_code_id": 99999,
    "tax_code": "99999"
  },
  "display_option": {
    "is_size_chart": false,
    "is_warranty": false
  },
  "fulfillment": {
    "is_po_box_prohibited": true,
    "po_box_prohibited_message": "We regret that this item cannot be shipped to PO Boxes."
  },
  "package_dimensions": {
    "weight": "0.18",
    "weight_unit_of_measure": "POUND",
    "width": "5.33",
    "depth": "6.65",
    "height": "0.46",
    "dimension_unit_of_measure": "INCH"
  },
  "environmental_segmentation": {
    "is_lead_disclosure": false
  },
  "manufacturer": {},
  "product_vendors": [
    {
      "id": "1258411",
      "manufacturer_style": "61119422"
    },
    {
      "id": "4667999",
      "manufacturer_style": "61119422"
    },
    {
      "id": "1258738",
      "manufacturer_style": "025192110306"
    }
  ],
  "product_classification": {
    "product_type": "542",
    "product_type_name": "ELECTRONICS",
    "item_type_name": "Movies",
    "item_type": {
      "category_type": "Item Type: MMBV",
      "type": 300752,
      "name": "Movies"
    }
  },
  "product_brand": {
    "brand": "Universal Home Video"
  },
  "item_state": "READY_FOR_LAUNCH",
  "specifications": [],
  "attributes": {
    "gift_wrapable": "N",
    "has_prop65": "N",
    "is_hazmat": "N",
    "max_order_qty": 10,
    "street_date": "2011-11-15",
    "media_format": "Blu-ray",
    "merch_class": "MOVIES",
    "merch_subclass": 34,
    "return_method": "This item can be returned to any Target store or Target.com."
  },
  "country_of_origin": "US",
  "relationship_type_code": "Title Authority Child",
  "subscription_eligible": false,
  "ribbons": [],
  "tags": [],
  "estore_item_status_code": "A",
  "eligibility_rules": {
    "hold": {
      "is_active": true
    },
    "ship_to_guest": {
      "is_active": true
    }
  },
  "return_policies": {
    "user": "Regular Guest",
    "policyDays": "30",
    "guestMessage": "This item must be returned within 30 days of the ship date. See return policy for details."
  },
  "gifting_enabled": false
}

 */
