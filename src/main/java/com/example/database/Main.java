package com.example.database;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            MySQLDatabase db = new MySQLDatabase();

            int failedAttempts = 0;
            User user = null;

            while (failedAttempts < 3) {
                System.out.print("Enter username: ");
                String userId = sc.nextLine();
                System.out.print("Enter password: ");
                String password = sc.nextLine();

                user = db.authenticateUser(userId, password);
                if (user != null) {
                    System.out.println("✅ Welcome, " + user.getFirstName() + "! You are logged in as " + user.getRole());
                    break;
                } else {
                    failedAttempts++;
                    System.out.println("❌ Login failed. Attempt " + failedAttempts + " of 3.");
                }
            }

            if (failedAttempts == 3) {
                System.out.println("\n😈 Too many failed attempts...");
                fakeMalwareLoading();
                System.out.println("💀 Just kidding! But seriously, stop guessing passwords.");
                return;
            }


            // Show permissions
            System.out.println("\nAvailable actions for your role:");
            switch (user.getRole()) {
                case "Admin" -> System.out.println("fetch, put, post, remove, adduser");
                case "Editor" -> System.out.println("fetch, put, post");
                case "General" -> System.out.println("fetch");
                default -> System.out.println("You have no permissions.");
            }

            // Show equipment table
            db.displayAllEquipment();

            // Interactive command loop
            while (true) {
                System.out.print("\nType an action (fetch, put, post, remove, adduser) or 'exit': ");
                String input = sc.nextLine().toLowerCase();

                if (input.equals("exit")) break;

                try {
                    switch (input) {
                        case "fetch" -> {
                            System.out.print("Enter EquipID to fetch: ");
                            int id = Integer.parseInt(sc.nextLine());
                            Equipment eq = new Equipment(id);
                            eq.setCurrentUser(user);
                            eq.fetchA();
                            eq.printEquipment();
                        }

                        case "put" -> {
                            System.out.print("Enter EquipID to update: ");
                            int id = Integer.parseInt(sc.nextLine());

                            System.out.print("New name: ");
                            String name = sc.nextLine();

                            System.out.print("New description: ");
                            String desc = sc.nextLine();

                            System.out.print("New capacity: ");
                            int cap = Integer.parseInt(sc.nextLine());

                            Equipment eq = new Equipment(id, name, desc, cap);
                            eq.setCurrentUser(user);
                            eq.putA();
                        }

                        case "post" -> {
                            System.out.print("New EquipID: ");
                            int newId = Integer.parseInt(sc.nextLine());

                            System.out.print("Name: ");
                            String name = sc.nextLine();

                            System.out.print("Description: ");
                            String desc = sc.nextLine();

                            System.out.print("Capacity: ");
                            int cap = Integer.parseInt(sc.nextLine());

                            Equipment newEq = new Equipment(newId, name, desc, cap);
                            newEq.setCurrentUser(user);
                            newEq.postA();
                        }

                        case "remove" -> {
                            System.out.print("Enter EquipID to delete: ");
                            int id = Integer.parseInt(sc.nextLine());

                            Equipment eq = new Equipment(id);
                            eq.setCurrentUser(user);
                            eq.removeA();
                        }

                        case "adduser" -> {
                            if (!user.getRole().equalsIgnoreCase("Admin")) {
                                System.out.println("🚫 Only Admins can add users.");
                                break;
                            }

                            System.out.print("New Username (Id): ");
                            String newId = sc.nextLine();

                            System.out.print("First Name: ");
                            String firstName = sc.nextLine();

                            System.out.print("Last Name: ");
                            String lastName = sc.nextLine();

                            System.out.print("Password: ");
                            String newPassword = sc.nextLine();

                            System.out.print("Role (Admin/Editor/General): ");
                            String role = sc.nextLine();

                            System.out.print("Org Unit: ");
                            String org = sc.nextLine();

                            User newUser = new User(newId, firstName, lastName, null, role, org);
                            db.addUser(newUser, newPassword);
                        }

                        default -> System.out.println("❓ Unknown command.");
                    }

                    // Show updated table
                    db.displayAllEquipment();

                } catch (DLException e) {
                    if (e.getMessage().contains("Unauthorized")) {
                        System.out.println("🚫 Access denied. You are not allowed to perform this action.");
                    } else {
                        System.out.println("❌ Something went wrong. Check logs for details.");
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid number format. Please try again.");
                }
            }

            System.out.println("👋 Logged out.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


















































    // "Hidden" malware fun method
    private static void fakeMalwareLoading() throws InterruptedException {
        String[] stages = {
                "[▓          ]", "[▓▓         ]", "[▓▓▓        ]",
                "[▓▓▓▓       ]", "[▓▓▓▓▓      ]", "[▓▓▓▓▓▓     ]",
                "[▓▓▓▓▓▓▓    ]", "[▓▓▓▓▓▓▓▓   ]", "[▓▓▓▓▓▓▓▓▓  ]", "[▓▓▓▓▓▓▓▓▓▓]"
        };

        System.out.println("\nInstalling malware.exe...");
        for (String stage : stages) {
            System.out.print("\r" + stage);
            Thread.sleep(250);
        }

        System.out.println("\n💣 Malware installed. System compromised...");
        Thread.sleep(1200);
    }
}
