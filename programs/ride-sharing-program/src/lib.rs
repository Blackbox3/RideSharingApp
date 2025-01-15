use anchor_lang::prelude::*;

// Define the program ID
declare_id!("8Dt4sQ4vcm5zRcZTcatkijh14pEXvcgmXd6MKz6hg8Hy");

#[program]
mod ride_sharing_program {
    use super::*;

    pub fn register_driver(ctx: Context<RegisterDriver>, name: String) -> Result<()> {
        let driver = &mut ctx.accounts.driver;

        driver.name = name;
        driver.available = true;
        Ok(())
    }

    pub fn book_ride(ctx: Context<BookRide>) -> Result<()> {
        let driver = &mut ctx.accounts.driver;
        require!(driver.available, RideSharingError::DriverNotAvailable);
        driver.available = false;
        Ok(())
    }

    pub fn complete_ride(ctx: Context<CompleteRide>) -> Result<()> {
        let driver = &mut ctx.accounts.driver;
        driver.available = true;
        Ok(())
    }
}

// Define the driver account structure
#[account]
pub struct Driver {
    pub name: String, // Name of the driver
    pub available: bool, // Availability status
}

// Context for registering a driver
#[derive(Accounts)]
pub struct RegisterDriver<'info> {
    #[account(init, payer = user, space = 8 + 32 + 1)] // Allocate space for the driver struct
    pub driver: Account<'info, Driver>,
    #[account(mut)]
    pub user: Signer<'info>, // The payer account
    pub system_program: Program<'info, System>, // Required for initialization of accounts
}

// Context for booking a ride
#[derive(Accounts)]
pub struct BookRide<'info> {
    #[account(mut)]
    pub driver: Account<'info, Driver>, // Driver account needs to be mutable
}

// Context for completing a ride
#[derive(Accounts)]
pub struct CompleteRide<'info> {
    #[account(mut)]
    pub driver: Account<'info, Driver>, // Driver account needs to be mutable
}

// Define custom errors for the program
#[error_code]
pub enum RideSharingError {
    #[msg("Driver is not available for booking.")]
    DriverNotAvailable,
}
