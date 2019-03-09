package pl.coderstrust.database.nosql.model;

public enum NoSqlVat {
  VAT_0(0.00f),
  VAT_5(0.05f),
  VAT_8(0.08f),
  VAT_23(0.23f);

  private float value;

  NoSqlVat(float value) {
    this.value = value;
  }

  public float getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "NoSqlVat{" +
            "value=" + value +
            '}';
  }
}
