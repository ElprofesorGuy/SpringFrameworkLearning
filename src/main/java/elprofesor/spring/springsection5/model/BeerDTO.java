package elprofesor.spring.springsection5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class BeerDTO {
    private UUID id;
    private Integer version;

    @NotBlank
    @NotNull
    private String beerName;
    @NotBlank
    @NotNull
    private String upc;

    @NotNull
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public UUID getId() {
        return this.id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public String getBeerName() {
        return this.beerName;
    }

    public String getUpc() {
        return this.upc;
    }

    public Integer getQuantityOnHand() {
        return this.quantityOnHand;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public LocalDateTime getUpdateDate() {
        return this.updateDate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BeerDTO)) return false;
        final BeerDTO other = (BeerDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
        final Object this$beerName = this.getBeerName();
        final Object other$beerName = other.getBeerName();
        if (this$beerName == null ? other$beerName != null : !this$beerName.equals(other$beerName)) return false;
        final Object this$upc = this.getUpc();
        final Object other$upc = other.getUpc();
        if (this$upc == null ? other$upc != null : !this$upc.equals(other$upc)) return false;
        final Object this$quantityOnHand = this.getQuantityOnHand();
        final Object other$quantityOnHand = other.getQuantityOnHand();
        if (this$quantityOnHand == null ? other$quantityOnHand != null : !this$quantityOnHand.equals(other$quantityOnHand))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$createDate = this.getCreateDate();
        final Object other$createDate = other.getCreateDate();
        if (this$createDate == null ? other$createDate != null : !this$createDate.equals(other$createDate))
            return false;
        final Object this$updateDate = this.getUpdateDate();
        final Object other$updateDate = other.getUpdateDate();
        if (this$updateDate == null ? other$updateDate != null : !this$updateDate.equals(other$updateDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BeerDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $beerName = this.getBeerName();
        result = result * PRIME + ($beerName == null ? 43 : $beerName.hashCode());
        final Object $upc = this.getUpc();
        result = result * PRIME + ($upc == null ? 43 : $upc.hashCode());
        final Object $quantityOnHand = this.getQuantityOnHand();
        result = result * PRIME + ($quantityOnHand == null ? 43 : $quantityOnHand.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $createDate = this.getCreateDate();
        result = result * PRIME + ($createDate == null ? 43 : $createDate.hashCode());
        final Object $updateDate = this.getUpdateDate();
        result = result * PRIME + ($updateDate == null ? 43 : $updateDate.hashCode());
        return result;
    }

    public String toString() {
        return "Beer(id=" + this.getId() + ", version=" + this.getVersion() + ", beerName=" + this.getBeerName() + ", upc=" + this.getUpc() + ", quantityOnHand=" + this.getQuantityOnHand() + ", price=" + this.getPrice() + ", createDate=" + this.getCreateDate() + ", updateDate=" + this.getUpdateDate() + ")";
    }
}
