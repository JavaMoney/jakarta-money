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
* JAXRS
* CDI
* JPA
* JSF

## Bean Validator

JSR 380 is a specification of the Java API for bean validation, part of Jakarta EE and JavaSE. It ensures that the properties of a bean meet specific criteria, using annotations such as @NotNull, @Min, and @Max.

To make an integration with Java-money Jakarta-money has four annotations:

**CurrencyAccepted**: Informs the currencies that are allowed on validation. It works with `MonetaryAmount` and `CurrencyUnit`.

```java
@CurrencyAccepted(currencies = "BRL")
private MonetaryAmount money;
@CurrencyAccepted(currencies = "BRL")
private CurrencyUnit currencyUnit;
```

**CurrencyRejected**: Informs the currencies that are rejected on validation. It works with `MonetaryAmount` and `CurrencyUnit`.

```java
@CurrencyRejected(currencies = "BRL")
private MonetaryAmount money;
@CurrencyRejected(currencies = "BRL")
private CurrencyUnit currencyUnit;
```
**MonetaryMax**: Informs the maximum value of a `MonetaryAmount`.

```java
@MonetaryMax("10.12")
private MonetaryAmount money;
```
**MonetaryMin**: Informs the minimum value of a `MonetaryAmount`.
```java
@MonetaryMin("10.12")
private MonetaryAmount money;
```

## JAXRS

If you want JAX-RS to automatically convert parameters such as query params, path params, or others, you need to create a ParamConverter.

It has support to:

* CurrencyUnit
* MonetaryAmount

```java
@Path("monetary")
@ApplicationScoped
public class MoneyResource {

    @GET
    public String findByCurrency(@QueryParam("currency") CurrencyUnit currency) {
        return "hello, " + currency;
    }
}
```

## JPA

Jakarta Persistence defines a standard for managing persistence and object/relational mapping in Java(R) environments.

It has support to both: `MoneyAmount` and `CurrencyUnit`.

```java
@Column
@Convert(converter = CurrencyConverter.class)
private CurrencyUnit currency;

@Column
@Convert(converter = MoneyConverter.class)
private MonetaryAmount money;
```

## CDI

### Currency Qualifier

The Currency annotation allows to inject a currency unit based in the  Currency code.

```java
@Inject
@Currency("USD")
private CurrencyUnit currency;
```

