//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.LineItemDTO;
import sharedrmi.application.dto.ShoppingCartDTO;

public interface ShoppingCartService extends Remote {
    ShoppingCartDTO getCart() throws RemoteException;

    void addProductToCart(AlbumDTO var1, int var2) throws RemoteException;

    void changeQuantity(LineItemDTO var1, int var2) throws RemoteException;

    void removeProductFromCart(LineItemDTO var1) throws RemoteException;
}
