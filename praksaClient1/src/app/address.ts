export class Address {

    street: string;
    city: string;
    zipCode: number;
    country: string;

    constructor(street: string, city: string, zipCode: number, country: string){
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
}
