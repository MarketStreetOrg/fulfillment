package org.katale.domains;

public class Product
    {
        public int ID;
        public String Name;
        public String Description ;     
        public String SKU;
        public int Quantity;

        public static class Builder
        {
            private Product product = new Product();

            public Builder SetName(String Name)
            {
                product.Name = Name;

                return this;
            }

            public Builder SetDescription(String Description)
            {
                product.Description = Description;

                return this;
            }

            public Builder SetSKU(String SKU)
            {
                product.SKU = SKU;

                return this;
            }

            public Builder SetQuantity(int Quantity)
            {
                product.Quantity = Quantity;

                return this;
            }


            public Product Build()
            {
                return product;
            }
        }
    }