package client;

import client.views.Template;
import protocol.Protocol;
import server.entities.Driver;
import server.entities.Vehicle;

import java.util.Scanner;

public class Client {
    private static final Scanner scan = new Scanner(System.in);
    private Protocol protocol = new Protocol();

    public void start() {
        Template.header("Bem Vindo! O que quer fazer?");
        int control = 5;
        do {
            Template.menu();
            System.out.print("Enter: ");
            control = scan.nextInt();
            switch (control) {
                case 1: {
                    // inserir veículo
                    this.inserir();
                    break;
                }
                case 2: {
                    // Listar
                    this.list();
                    break;
                }
                case 3: {
                    // Remover
                    this.remove();
                    break;
                }
                case 4: {
                    // Atualizar
                    this.update();
                    break;
                }
                case 5: {
                    control = 5;
                    break;
                }
                case 6: {
                    System.out.println("Quantidade de elementos cadastrados: " + this.getLength());
                    break;
                }
                case 7: {
                    this.getOne();
                    break;
                }
                case 8: {
                    this.getAllLicensePlate();
                    break;
                }
                default: {
                    System.out.println("Opção inválida! Tente novamente");
                    break;
                }
            }
        } while (control != 5);
        Template.footer();
    }

    private void inserir() {
        System.out.println("Por favor, digite o que se pede abaixo.");
        System.out.print("Renavam: ");
        long renavam = scan.nextLong();
        scan.nextLine();
        System.out.print("Placa: ");
        String placa = scan.nextLine();
        System.out.print("Modelo: ");
        String modelo = scan.nextLine();
        System.out.print("Data de fabricação: ");
        String dataFabricaca = scan.nextLine();
        System.out.print("Nome do condutor: ");
        String nomeCondutor = scan.nextLine();
        System.out.print("CPF do condutor: ");
        String cpfCondutor = scan.nextLine();
        var vehicle = new Vehicle(
            placa,
            renavam,
            modelo,
            dataFabricaca,
            new Driver(nomeCondutor, cpfCondutor)
        );
        this.protocol.request(0, vehicle);
    }

    private void list() {
        this.protocol.request(2, null);
    }

    private void remove() {
        Vehicle vehicle = new Vehicle();
        System.out.print("Para deletar digite o renavam do veículo desejado: ");
        long renavam = scan.nextLong();
        vehicle.setRenavam(renavam);
        this.protocol.request(1, vehicle);
    }

    private void update() {
        System.out.print("Para atualizar um Veículo informe seu Renavam: ");
        long renavam = scan.nextLong();
        scan.nextLine();
        System.out.println("Agora digite os novos dados: ");
        System.out.print("Placa: ");
        String placa = scan.nextLine();
        System.out.print("Modelo: ");
        String modelo = scan.nextLine();
        System.out.print("Data de fabricação: ");
        String dataFabricaca = scan.nextLine();
        System.out.print("Nome do condutor: ");
        String nomeCondutor = scan.nextLine();
        System.out.print("CPF do condutor: ");
        String cpfCondutor = scan.nextLine();
        var vehicle = new Vehicle(placa, renavam, modelo, dataFabricaca, new Driver(nomeCondutor, cpfCondutor));
        this.protocol.request(7, vehicle);
        if (this.protocol.responseBool()) {
            System.out.println("Atualizado com Sucesso.");
        } else {
            System.out.println("Veículo não encontrado");
        }
    }

    private int getLength() {
        this.protocol.request(6, null);
        return this.protocol.response();
    }

    private void getOne() {
        System.out.print("Digite o renavam do veículo desejado: ");
        long renavam = scan.nextLong();
        var auxVechicle = new Vehicle();
        auxVechicle.setRenavam(renavam);
        this.protocol.request(8, auxVechicle);
        var vehicle = this.protocol.responseVehicle();
        if (vehicle == null) {
            System.out.println("Veículo não encontrado!");
        } else {
            System.out.println(
                " Placa: " + vehicle.getLicencePlate() + 
                "; Modelo: " + vehicle.getName() + 
                "; Renavam: " + vehicle.getRenavam() + 
                "; Data de fabricação: " + vehicle.getManufacturingDate() + 
                "; Nome do condutor: " + vehicle.getDriver().getName() + 
                "; CPF do condutor " + vehicle.getDriver().getCpf()
            );
        }
    }

    private void getAllLicensePlate() {
        var vehicle = new Vehicle();
        scan.nextLine();
        System.out.print("Digite a placa: ");
        String licensePlate = scan.nextLine();
        vehicle.setLicencePlate(licensePlate);
        this.protocol.request(4, vehicle);
        var response = this.protocol.responseNo();
        System.out.println(
            " Placa: " + response.getVehicle().getLicencePlate() + 
            "; Modelo: " + response.getVehicle().getName() + 
            "; Renavam: " + response.getVehicle().getRenavam() + 
            "; Data de fabricação: " + response.getVehicle().getManufacturingDate() + 
            "; Nome do condutor: " + response.getVehicle().getDriver().getName() + 
            "; CPF do condutor " + response.getVehicle().getDriver().getCpf()
        );
    }
}