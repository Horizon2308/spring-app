export class ProviderDTO {
    provider_name: string
    constructor(data: any) {
        this.provider_name = data.provider_name;
    }
}