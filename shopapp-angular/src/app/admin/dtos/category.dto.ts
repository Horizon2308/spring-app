export class CategoryDTO {
  category_name: string;

  constructor(data: any) {
    this.category_name = data.category_name;
  }
}
