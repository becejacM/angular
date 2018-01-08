import { MyComponent } from './my-component'
import { Address } from './address'

export class MyConfiguration {

    name: string
    components: MyComponent[]
    userId: number;
    payAddress: Address;
    deliverAddress: Address;
    methodOfPayment: string;

    constructor(name: string, components: MyComponent[], id: number) {
        this.name = name;
        this.components = components;
        this.userId = id;
    }

}
