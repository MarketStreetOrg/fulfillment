package org.katale.domains;

import java.util.Date;
import java.util.HashSet;

public class Order
    {
        private String OrderNumber;
        private String CustomerNumber;
        private String OrderStatus;
        private HashSet<Product> Products = new HashSet<Product>();
        private Date DateCreated;

        public static class Builder
        {
            Order order = new Order();

            public Builder()
            {
                order.Products = new HashSet<Product>();
            }

            public Builder setOrderNumber(String OrderNumber)
            {
                order.OrderNumber = OrderNumber;
                return this;
            }
            public Builder setCustomerNumber(String customerNumber)
            {
                order.CustomerNumber = customerNumber;
                return this;
            }

            public Builder setStatus(String orderStatus)
            {
                order.OrderStatus = orderStatus;
                return this;
            }

            public Builder addItem(String sku, int quantity)
            {

                Product product = new Product.Builder()
                                    .SetSKU(sku)
                                    .SetQuantity(quantity)
                                    .Build();

                order.Products.add(product);

                return this;
            }

            public Builder setDate(Date date)
            {
                order.DateCreated = date;
                return this;
            }

            public Order Build()
            {
                return order;
            }

        }
    }