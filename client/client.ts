import * as anchor from "@coral-xyz/anchor";
import * as web3 from "@solana/web3.js";

// Load environment variables from the .env file (if you are using the .env method)
require('dotenv').config();

// Configure the client to use the local cluster (localnet, devnet, or testnet)
anchor.setProvider(anchor.AnchorProvider.env());

// Get the program type from the workspace
const program = anchor.workspace.RideSharingProgram as anchor.Program;

// Function to interact with the program
async function interact() {
  try {
    // Generate a new keypair for the driver account
    const driverAccount = new web3.Keypair();

    // Driver name
    const driverName = "John Doe";

    // Send transaction to register the driver
    const tx = await program.methods
      .registerDriver(driverName)
      .accounts({
        driver: driverAccount.publicKey,
        user: program.provider.publicKey,
        systemProgram: web3.SystemProgram.programId,
      })
      .signers([driverAccount])
      .rpc();

    console.log(`Transaction hash: ${tx}`);

    // Confirm the transaction
    const confirmation = await program.provider.connection.confirmTransaction(tx);
    console.log('Transaction confirmed:', confirmation);

    // Fetch the created driver account (if needed)
    const driver = await program.account.driver.fetch(driverAccount.publicKey);
    console.log("Driver Account:", driver);

  } catch (error) {
    console.error("Error interacting with the program:", error);
  }
}

interact();
