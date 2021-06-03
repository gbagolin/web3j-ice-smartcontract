package Model;

import java.math.BigInteger;
import java.util.List;

public class ProductInformation extends Product {
    public List<PhaseWithAllInformation> phases;
    public Recipe recipe;
    public Company company;
    public ProductInformation(Product product,
                              Company company,
                              List<PhaseWithAllInformation> phases,
                              Recipe recipe
    ) {
        super(product.id,
                product.name,
                product.description,
                product.companyId,
                product.recipeId);
        this.phases = phases;
        this.recipe = recipe;
        this.company = company;
    }
}
