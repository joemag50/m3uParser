
public class Info {
	public String info;
	public String url;
	
	public String id;
	public String name;
	public String logo;
	public String grouptitle;
	
	public String tags;
	
	Info(String info, String url)
	{
		this.info = info.substring(21);
		this.url = url;
		parseo();
	}

	public void parseo()
	{
		this.id = "";
		
		this.name = this.info.split("tvg-name=\"")[1];
		this.name = this.name.substring(0, this.name.indexOf('\"'));
		
		this.logo = this.info.split("tvg-logo=\"")[1];
		this.logo = this.logo.substring(0, this.logo.indexOf('\"'));
		
		if (this.logo.length() > 100)
		{
			try
			{
				this.logo = this.info.split("src=\"")[1];
				this.logo = this.logo.substring(0, this.logo.indexOf('\"'));
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				this.logo = "";
			}
		}
		
		this.grouptitle = this.info.split("group-title=\"")[1];
		this.grouptitle = this.grouptitle.substring(0, this.grouptitle.indexOf('\"'));
	}

	static public int TipoLinea(String linea)
	{
		String inicio = linea.substring(0, 7);
		//Es encabezado
		if (inicio.equals(new String("#EXTM3U")))
		{
			return 0;
		}
		
		//Es info o video
		if (inicio.equals(new String("#EXTINF")))
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
}
