package es.unileon.happycow.utils.navigation.menu;

public class NavMenuDrawerItem {
	
	private String title;
	private int icon;
	
	public NavMenuDrawerItem(){}

	public NavMenuDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}

}
