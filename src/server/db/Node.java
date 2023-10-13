package server.db;

import server.entities.Vehicle;

public class Node {
  private long key;
  private Vehicle vehicle;
  private Node next;

  public Node() {}

  public Node(Vehicle vehicle, Node p) {
    this.vehicle = vehicle;
    this.key = this.getVehicle().getRenavam();
    this.next = p;
  }

  public long getKey() {
    return key;
  }

  public void setKey(long key) {
    this.key = key;
  }

  public Node getNext() {
    return next;
  }

  public void setNext(Node next) {
    this.next = next;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  @Override
	public String toString() {
		return "Placa: " + this.vehicle.getLicencePlate() + 
		"; Modelo: " + this.vehicle.getName() + 
		"; Renavam: " + this.vehicle.getRenavam() + 
		"; Data de fabricação: " + this.vehicle.getManufacturingDate() + 
		"; Nome do condutor: " + this.vehicle.getDriver().getName() + 
		"; CPF do condutor " + this.vehicle.getDriver().getCpf();
	}
}