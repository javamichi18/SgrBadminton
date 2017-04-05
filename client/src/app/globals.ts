/**
 * Created by Max on 30.03.2017.
 */
import { Spieler } from './spieler';
import {Observable} from "rxjs";

export class Globals {
   private loggedIn: Observable<Spieler>;
   public setLoggedIn( login: Observable<Spieler> ): void {
       this.loggedIn = login;
   }
   public getLoggedIn(): Observable<Spieler> {
       return this.loggedIn;
   }
}
