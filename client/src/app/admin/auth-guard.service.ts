import {Injectable} from "@angular/core";
import {CanActivate} from "@angular/router";
import {Globals} from "../globals";

@Injectable()
export class AuthGuard implements CanActivate {
    private globals: Globals;

    canActivate() {
        self.console.log('globals = ' + this.globals + " / " + this.globals.getLoggedIn()[0].id);

        let user = this.globals.getLoggedIn();
        if (user && user[0].admin) {
            let isAdmin = user[0].admin;
            self.console.log('AuthGuard#canActivate: ' + user[0].nachname + ' > ' + isAdmin);
            return isAdmin;
        }

        // TODO: false! use
        return true;
    }

    constructor(globals: Globals) {
        this.globals = globals;
    }
}