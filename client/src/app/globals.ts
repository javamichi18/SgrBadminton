/**
 * Created by Max on 30.03.2017.
 */
import { Spieler } from './spieler';
export class Globals {
  private loggedIn: Spieler;
  public setLoggedIn( login: Spieler ): void {
    this.loggedIn = login;
  }
  public getLoggedIn(): Spieler {
    return this.loggedIn;
  }
}
