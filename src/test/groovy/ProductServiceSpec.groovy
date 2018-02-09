

import com.myretail.dao.PriceDao
import com.myretail.dao.ProductDao
import com.myretail.service.ProductService
import ratpack.test.exec.ExecHarness
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject


@Ignore
class ProductServiceSpec {


  private ProductService productService
  private ProductDao productDao
  private PriceDao priceDao


  void setup
  {
    priceDao = Mock(PriceDao)
    productDao = Mock(ProductDao)
    productService = new ProductService(priceDao, productDao)
  }

  @Ignore
  void 'even numbers must be transformed with mapIf'() {
    when:
    final result = ExecHarness.yieldSingle {
      productService.addPriceById(id, price)
    }

    then:
    result.value == expected

    where:
    id          | price
    startValue || expected
    1          || 1
    2          || 4
    3          || 3
    4          || 16
  }

  @Ignore
  void 'ten-th numbers must be transformed with flatMapIf'() {
    when:
    final result = ExecHarness.yieldSingle {
      numberService.multiplyTens(startValue)
    }

    then:
    result.value == expected

    where:
    startValue || expected
    1          || 1
    10         || 100
    2          || 2
    20         || 400
  }
}
