def extract_information(property_string: str) -> dict:
    # Split the input string into a list of values
    values = property_string.split(",")
    print(values)
    # Extract relevant values
    prop_id = values[0]
    full_address = values[1]
    bedrooms = int(values[2])
    bathrooms = int(values[3])
    parking_spaces = int(values[4])
    latitude = float(values[5])
    longitude = float(values[6])
    floor_number = int(values[7]) if values[7] else None
    land_area = int(values[8]) if values[8] else None
    floor_area = int(values[9])
    price = int(values[10])
    property_features = values[11].split(";") if values[11] else []

    # Determine the property type
    if "/" in full_address:
        prop_type = "apartment"
    else:
        prop_type = "house"

    # Extract the suburb
    address_parts = full_address.split(" ")
    suburb = address_parts[-3]

    # Create the dictionary
    property_dict = {
        "prop_id": prop_id,
        "prop_type": prop_type,
        "full_address": full_address,
        "suburb": suburb,
        "bedrooms": bedrooms,
        "bathrooms": bathrooms,
        "parking_spaces": parking_spaces,
        "latitude": latitude,
        "longitude": longitude,
        "floor_area": floor_area,
        "price": price,
        "property_features": property_features
    }

    # Include floor_number or land_area based on property type
    if prop_type == "apartment":
        property_dict["floor_number"] = floor_number
    else:
        property_dict["land_area"] = land_area

    return property_dict


def add_feature(property_dict: dict, feature: str) -> None:
    if feature not in property_dict["property_features"]:
        property_dict["property_features"].append(feature)


def remove_feature(property_dict: dict, feature: str) -> None:
    if feature in property_dict["property_features"]:
        property_dict["property_features"].remove(feature)


def main():
    sample_string = "P10001,3 Antrim Place Langwarrin VIC 3910,4,2,2,-38.16655678,145.1838435,,608,257,870000,dishwasher;central heating"
    property_dict = extract_information(sample_string)
    print(f"The first property is at {property_dict['full_address']} and is valued at ${property_dict['price']}")

    sample_string_2 = "P10002,G01/7 Rugby Road Hughesdale VIC 3166,2,1,1,-37.89342337,145.0862616,1,,125,645000,dishwasher;air conditioning;balcony"
    property_dict_2 = extract_information(sample_string_2)
    print(f"The second property is in {property_dict_2['suburb']} and is located on floor {property_dict_2['floor_number']}")

    add_feature(property_dict, "electric hot water")
    print(f"Property {property_dict['prop_id']} has the following features: {property_dict['property_features']}")

    remove_feature(property_dict, "central heating")
    print(f"After removing 'central heating', property {property_dict['prop_id']} now has the following features: {property_dict['property_features']}")


if __name__ == '__main__':
    main()
