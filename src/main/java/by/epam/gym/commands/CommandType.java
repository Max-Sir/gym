package by.epam.gym.commands;


import by.epam.gym.commands.admin.FindClientByNameCommand;
import by.epam.gym.commands.admin.ShowAllClientsCommand;
import by.epam.gym.commands.client.*;
import by.epam.gym.commands.common.ChangeLanguageCommand;
import by.epam.gym.commands.common.LoginCommand;
import by.epam.gym.commands.common.LogoutCommand;
import by.epam.gym.commands.common.RegisterCommand;
import by.epam.gym.commands.special.*;
import by.epam.gym.commands.trainer.*;

/**
 * Types of commands.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 */
public enum CommandType {

    /**
     * Common commands.
     */
    COMMON_LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    COMMON_LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    COMMON_REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    COMMON_CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },

    /**
     * Special commands.
     */
    SPECIAL_SHOW_CLIENT_ORDERS {
        {
            this.command = new ShowClientOrdersCommand();
        }
    },
    SPECIAL_SHOW_CLIENT_TRAINING_PROGRAM {
        {
            this.command = new ShowClientTrainingProgramCommand();
        }
    },
    SPECIAL_EDIT_TRAINING_PROGRAM {
        {
            this.command = new EditTrainingProgramCommand();
        }
    },
    SPECIAL_EDIT_EXERCISE_IN_TRAINING_PROGRAM {
        {
            this.command = new EditExerciseInTrainingProgramCommand();
        }
    },
    SPECIAL_DELETE_EXERCISE_FROM_TRAINING_PROGRAM {
        {
            this.command = new DeleteExerciseFromTrainingProgramCommand();
        }
    },
    SPECIAL_DELETE_DAY_FROM_TRAINING_PROGRAM {
        {
            this.command = new DeleteDayFromTrainingProgramCommand();
        }
    },
    SPECIAL_ADD_EXERCISE_TO_TRAINING_PROGRAM {
        {
            this.command = new AddExerciseToTrainingProgramCommand();
        }
    },
    SPECIAL_ADD_DAY_TO_TRAINING_PROGRAM {
        {
            this.command = new AddDayToTrainingProgramCommand();
        }
    },
    SPECIAL_SAVE_TRAINING_PROGRAM_EDIT {
        {
            this.command = new SaveTrainingProgramCommand();
        }
    },
    SPECIAL_EDIT_DIET_IN_TRAINING_PROGRAM {
        {
            this.command = new EditDietInTrainingProgramCommand();
        }
    },

    /**
     * Client's commands.
     */
    CLIENT_ADD_FEEDBACK {
        {
            this.command = new AddFeedbackCommand();
        }
    },
    CLIENT_PREPARE_ORDER {
        {
            this.command = new PrepareOrderCommand();
        }
    },
    CLIENT_PAY_ORDER {
        {
            this.command = new PayOrderCommand();
        }
    },
    CLIENT_REFUSE_TRAINING_PROGRAM {
        {
            this.command = new RefuseTrainingProgramCommand();
        }
    },

    CLIENT_CHECK_ACTUAL_ORDER{
        {
            this.command = new CheckClientActualOrderCommand();
        }
    },

    /**
     * Admin commands.
     */
    ADMIN_FIND_CLIENT_BY_NAME {
        {
            this.command = new FindClientByNameCommand();
        }
    },
    ADMIN_SHOW_ALL_CLIENTS {
        {
            this.command = new ShowAllClientsCommand();
        }
    },

    /**
     * Trainer commands.
     */
    TRAINER_SHOW_PERSONAL_CLIENTS {
        {
            this.command = new ShowPersonalClientsCommand();
        }
    },
    TRAINER_PREPARE_TRAINING_PROGRAM_CREATION {
        {
            this.command = new PrepareTrainingProgramCreationCommand();
        }
    },
    TRAINER_CREATE_EXERCISE {
        {
            this.command = new CreateExerciseCommand();
        }
    },
    TRAINER_CREATE_TRAINING_PROGRAM {
        {
            this.command = new CreateTrainingProgramCommand();
        }
    },
    TRAINER_FINISH_TRAINING_PROGRAM_CREATION {
        {
            this.command = new FinishTrainingProgramCreationCommand();
        }
    };

    /**
     * Current command.
     */
    ActionCommand command;

    /**
     * Gets current commands.
     *
     * @return the current commands.
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
