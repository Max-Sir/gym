package by.epam.gym.commands;


import by.epam.gym.commands.admin.FindUserByNameCommand;
import by.epam.gym.commands.admin.ShowAllClientsCommand;
import by.epam.gym.commands.client.AddFeedbackCommand;
import by.epam.gym.commands.client.ShowOrdersCommand;
import by.epam.gym.commands.common.DescribeTrainingProgramCommand;
import by.epam.gym.commands.common.LoginCommand;
import by.epam.gym.commands.common.LogoutCommand;
import by.epam.gym.commands.common.RegisterCommand;
import by.epam.gym.commands.trainer.*;

/**
 * Types of commands.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 */
public enum CommandType {

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
            command = new RegisterCommand();
        }
    },
    ADMIN_FIND_USER_BY_NAME{
        {
            this.command = new FindUserByNameCommand();
        }
    },
    ADMIN_SHOW_ALL_CLIENTS{
        {
            this.command = new ShowAllClientsCommand();
        }
    },
    TRAINER_CREATE_EXERCISE {
        {
            this.command = new CreateExerciseCommand();
        }
    },
    TRAINER_SHOW_PERSONAL_CLIENTS{
        {this.command = new ShowPersonalClientsCommand();}
    },
    COMMON_DESCRIBE_TRAINING_PROGRAM{
        {this.command = new DescribeTrainingProgramCommand();}
    },
    TRAINER_PREPARE_TRAINING_PROGRAM_CREATION{
        {
            this.command = new PrepareTrainingProgramCreationCommand();
        }
    },
    TRAINER_CREATE_TRAINING_PROGRAM{
        {this.command = new CreateTrainingProgramCommand();}
    },
    TRAINER_PREPARE_ADD_EXERCISE_TO_TRAINING_PROGRAM{
        {
            this.command = new PrepareAddExerciseToTrainingProgramCommand();
        }
    },
    TRAINER_ADD_EXERCISE_TO_TRAINING_PROGRAM{
        {
            this.command = new AddExerciseToTrainingProgramCommand();
        }
    },
    TRAINER_FINISH_TRAINING_PROGRAM_CREATION{
        {
            this.command = new FinishTrainingProgramCreationCommand();
        }
    },
    TRAINER_DELETE_EXERCISE_FROM_TRAINING_PROGRAM{
        {this.command = new DeleteExerciseFromTrainingProgramCommand();}
    },
    CLIENT_SHOW_ORDERS{
        {
            this.command = new ShowOrdersCommand();
        }
    },
    CLIENT_ADD_FEEDBACK{
        {
            this.command = new AddFeedbackCommand();
        }
    };
    /**
     * Current command.
     */
    ActionCommand command;

    /**
     * Gets current command.
     *
     * @return the current command.
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }

}
