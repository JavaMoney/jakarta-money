# jakarta-money

Jakarta money is a helpful library for a better developer experience when combining Money-API with Jakarta and MicroProfile API.

**The dependency:**

```xml
<dependency>
   <groupId>org.javamoney</groupId>
   <artifactId>jakarta</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</dependency>
```

The API brings some libraries that is split by specification:

* Bean Validator
* CDI
* JAXRS
* JPA
* JSF

## Hibernate Validator

JSR 380 is a specification of the Java API for bean validation, part of Jakarta EE and JavaSE. It ensures that the properties of a bean meet specific criteria, using annotations such as @NotNull, @Min, and @Max.

To make an integration with Java-money Jakarta-money has four annotations:

* **CurrencyAccepted**: Informs the currencies that are allowed on validation. It works with `MonetaryAmount` and `CurrencyUnit`.
* **CurrencyRejected**: Informs the currencies that are rejected on validation. It works with `MonetaryAmount` and `CurrencyUnit`.
* **MonetaryMax**: Informs the maximum value of a `MonetaryAmount`.
* **MonetaryMin**: Informs the minimum value of a `MonetaryAmount`.


