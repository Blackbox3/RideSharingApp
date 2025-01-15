import * as anchor from "@coral-xyz/anchor";
import assert from "assert";
import * as web3 from "@solana/web3.js";
import type { RideSharingProgram } from "../target/types/ride_sharing_program";

describe("Ride Sharing Program", () => {
  // Configure the client to use the local cluster
  anchor.setProvider(anchor.AnchorProvider.env());

  const program = anchor.workspace.RideSharingProgram as anchor.Program<RideSharingProgram>;
  
  it("Register a driver", async () => {
    // Generate a new keypair for the driver account
    const driverAccount = new web3.Keypair();

    // Driver name
    const driverName = "John Doe";

    // Send transaction to register the driver
    const txHash = await program.methods
      .registerDriver(driverName)
      .accounts({
        driver: driverAccount.publicKey, // Driver account public key
        user: program.provider.publicKey, // User account (payer)
        systemProgram: web3.SystemProgram.programId, // System program (required for account creation)
      })
      .signers([driverAccount]) // Signing the transaction with the driver account
      .rpc();

    console.log(`Use 'solana confirm -v ${txHash}' to see the logs`);

    // Confirm transaction
    await program.provider.connection.confirmTransaction(txHash);

    // Fetch the created driver account
    const driver = await program.account.driver.fetch(driverAccount.publicKey);

    console.log("Registered Driver Details:", driver);

    // Assert that the driver name and availability status are correct
    assert.equal(driver.name, driverName);
    assert.equal(driver.available, true);
  });

  it("Book a ride", async () => {
    // Generate a new keypair for the driver account
    const driverAccount = new web3.Keypair();

    // Register the driver first
    const driverName = "Available Driver";
    await program.methods
      .registerDriver(driverName)
      .accounts({
        driver: driverAccount.publicKey,
        user: program.provider.publicKey,
        systemProgram: web3.SystemProgram.programId,
      })
      .signers([driverAccount])
      .rpc();

    // Book the ride
    const txHash = await program.methods
      .bookRide()
      .accounts({
        driver: driverAccount.publicKey,
      })
      .rpc();

    console.log(`Use 'solana confirm -v ${txHash}' to see the logs`);

    // Fetch the updated driver account
    const driver = await program.account.driver.fetch(driverAccount.publicKey);

    console.log("Driver Details After Booking:", driver);

    // Assert that the driver is now unavailable
    assert.equal(driver.available, false);
  });

  it("Complete a ride", async () => {
    // Generate a new keypair for the driver account
    const driverAccount = new web3.Keypair();

    // Register the driver first
    const driverName = "Driver Completing Ride";
    await program.methods
      .registerDriver(driverName)
      .accounts({
        driver: driverAccount.publicKey,
        user: program.provider.publicKey,
        systemProgram: web3.SystemProgram.programId,
      })
      .signers([driverAccount])
      .rpc();

    // Book the ride
    await program.methods
      .bookRide()
      .accounts({
        driver: driverAccount.publicKey,
      })
      .rpc();

    // Complete the ride
    const txHash = await program.methods
      .completeRide()
      .accounts({
        driver: driverAccount.publicKey,
      })
      .rpc();

    console.log(`Use 'solana confirm -v ${txHash}' to see the logs`);

    // Fetch the updated driver account
    const driver = await program.account.driver.fetch(driverAccount.publicKey);

    console.log("Driver Details After Completing Ride:", driver);

    // Assert that the driver is now available
    assert.equal(driver.available, true);
  });
});
